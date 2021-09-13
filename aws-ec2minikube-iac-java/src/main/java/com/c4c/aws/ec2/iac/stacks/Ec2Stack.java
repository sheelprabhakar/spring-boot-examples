package com.c4c.aws.ec2.iac.stacks;

import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.amazon.awscdk.core.RemovalPolicy;
import software.amazon.awscdk.core.Stack;
import software.amazon.awscdk.services.ec2.*;
import software.amazon.awscdk.services.ecr.Repository;

import software.amazon.awscdk.services.iam.Role;
import software.amazon.awscdk.services.iam.ServicePrincipal;
import software.constructs.Construct;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;

public class Ec2Stack extends Stack {
    public Ec2Stack(@Nullable Construct scope, @Nullable String id) throws IOException {
        super(scope, id);

        Repository repository = Repository.Builder.create(this, "C4C-ECR-REPO")
                .repositoryName("2f-authentication-image")
                .removalPolicy(RemovalPolicy.DESTROY).build();

        SubnetConfiguration subnetConfiguration = SubnetConfiguration.builder()
                .subnetType(SubnetType.PUBLIC)
                .cidrMask(28)
                .name("C4C-EC2-PB-SUBNET-1").build();
        List<SubnetConfiguration> scList = new ArrayList<>();
        scList.add(subnetConfiguration);

        subnetConfiguration = SubnetConfiguration.builder()
                .subnetType(SubnetType.PRIVATE)
                .cidrMask(28)
                .name("C4C-EC2-PVT-SUBNET-1").build();
        //scList.add(subnetConfiguration);

        // Create VPC with a AZ limit of two.
        Vpc vpc = new Vpc(this, "C4C-EC2-VPC", VpcProps.builder().cidr("10.0.0.0/24")
                .maxAzs(1)
                .subnetConfiguration(scList)
                .natGateways(0)
                .build());

        SubnetSelection subnetSelection = SubnetSelection.builder()
                .subnets(vpc.selectSubnets().getSubnets()).build();
        // Create SG
        SecurityGroup securityGroup = SecurityGroup.Builder.create(this, "C4C-SG-EC2-CLUSTER-PB")
                .securityGroupName("C4C-SG-EC2-CLUSTER-PB")
                .vpc(vpc)
                .allowAllOutbound(false)
                .build();
        securityGroup.addIngressRule(Peer.anyIpv4(), Port.tcp(80), "Http from any where");
        securityGroup.addIngressRule(Peer.anyIpv4(), Port.tcp(22), "SSH from any where");
        securityGroup.addEgressRule(Peer.anyIpv4(), Port.allTcp(), "SSH from any where");
        Role role = Role.Builder.create(this, "C4C-EC2-ROLE-STACK")
                .assumedBy(new ServicePrincipal("ec2.amazonaws.com"))
                .description("Role to manage EKS cluster")
                .roleName("C4C-EC2-ROLE")
                .build();
        String userData = FileUtils.readFileToString(new File("sh/script.sh"), Charset.forName("UTF-8"));
        BlockDevice blockDevice = new BlockDevice() {
            @Override
            public @NotNull String getDeviceName() {
                return "/dev/xvda";
            }

            @Override
            public @NotNull BlockDeviceVolume getVolume() {
                return BlockDeviceVolume.ebs(20);
            }
        };
        List<BlockDevice> blockDevices = new ArrayList<>();
        blockDevices.add(blockDevice);
        Map<String, String> imageMap = new HashMap<>();
        imageMap.put("us-east-1", "ami-09e67e426f25ce0d7");
        @NotNull IMachineImage machineImage = MachineImage.genericLinux(imageMap);
        Instance instance = Instance.Builder.create(this, id).instanceName("C4C-EC2-MINIKUBE")
                .vpc(vpc)
                .instanceType(new InstanceType("t2.medium"))
                .keyName("c4c-key-pair")
                .securityGroup(securityGroup)
                .machineImage(machineImage)
                .userData(UserData.custom(userData) )
                .blockDevices(blockDevices)
                .build();
        CfnEIP cfnEIP = CfnEIP.Builder.create(this, "C4C-EC2-MINIKUBE-EIP")
                .instanceId(instance.getInstanceId())
                .build();

    }

}
