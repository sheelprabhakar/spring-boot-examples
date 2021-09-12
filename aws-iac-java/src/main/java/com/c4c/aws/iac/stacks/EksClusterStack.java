package com.c4c.aws.iac.stacks;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.amazon.awscdk.core.RemovalPolicy;
import software.amazon.awscdk.core.Stack;
import software.amazon.awscdk.services.ec2.*;
import software.amazon.awscdk.services.ecr.Repository;
import software.amazon.awscdk.services.eks.Cluster;
import software.amazon.awscdk.services.eks.DefaultCapacityType;
import software.amazon.awscdk.services.eks.EndpointAccess;
import software.amazon.awscdk.services.eks.KubernetesVersion;
import software.amazon.awscdk.services.iam.IManagedPolicy;
import software.amazon.awscdk.services.iam.ManagedPolicy;
import software.amazon.awscdk.services.iam.Role;
import software.amazon.awscdk.services.iam.ServicePrincipal;
import software.constructs.Construct;

import java.util.ArrayList;
import java.util.List;

public class EksClusterStack extends Stack {
    public EksClusterStack(@Nullable Construct scope, @Nullable String id) {
        super(scope, id);

        Repository repository = Repository.Builder.create(this, "C4C-ECR-REPO")
                .repositoryName("2f-authentication-image")
                .removalPolicy(RemovalPolicy.DESTROY).build();

        SubnetConfiguration subnetConfiguration = SubnetConfiguration.builder()
                .subnetType(SubnetType.PUBLIC)
                .cidrMask(28)
                .name("C4C-PB-SUBNET-1").build();
        List<SubnetConfiguration> scList = new ArrayList<>();
        scList.add(subnetConfiguration);

        subnetConfiguration = SubnetConfiguration.builder()
                .subnetType(SubnetType.PRIVATE)
                .cidrMask(28)
                .name("C4C-PVT-SUBNET-1").build();
        scList.add(subnetConfiguration);

        // Create VPC with a AZ limit of two.
        Vpc vpc = new Vpc(this, "C4C-VPC", VpcProps.builder().cidr("10.0.0.0/24")
                .maxAzs(2)
                .subnetConfiguration(scList)
                .build());
        List<SubnetSelection> subnetSelectionList = new ArrayList<>();
        SubnetSelection subnetSelection = SubnetSelection.builder()
                .subnets(vpc.selectSubnets().getSubnets()).build();
        subnetSelectionList.add(subnetSelection);

        // Create SG
        SecurityGroup securityGroup = SecurityGroup.Builder.create(this, "C4C-SG-EKS-CLUSTER-PB")
                .securityGroupName("C4C-SG-EKS-CLUSTER-PB")
                .vpc(vpc)
                .allowAllOutbound(false)
                .build();
        securityGroup.addEgressRule(Peer.anyIpv4(), Port.tcp(80), "Http from any where");

        // Create EKS cluster role
        List<@NotNull IManagedPolicy> policies = new ArrayList<>();
        @NotNull IManagedPolicy iManagedPolicy = ManagedPolicy.fromManagedPolicyArn(this, "c4c-AmazonEKSClusterPolicy", "arn:aws:iam::aws:policy/AmazonEKSClusterPolicy");
        policies.add(iManagedPolicy);

        Role role = Role.Builder.create(this, "C4C-EKS-ROLE-STACK")
                .assumedBy(new ServicePrincipal("eks.amazonaws.com"))
                .description("Role to manage EKS cluster")
                .roleName("C4C-EKS-ROLE")
                .managedPolicies(policies)
                .build();
        Cluster cluster = Cluster.Builder.create(this, id)
                .clusterName("C4C-EKS-CLUSTER")
                .version(KubernetesVersion.V1_21)
                .role(role)
                .defaultCapacity(1)
                .defaultCapacityInstance(new InstanceType("t2.small"))
                .defaultCapacityType(DefaultCapacityType.NODEGROUP)
                .vpc(vpc)
                .vpcSubnets(subnetSelectionList)
                .securityGroup(securityGroup)
                .endpointAccess(EndpointAccess.PUBLIC_AND_PRIVATE)
                .build();

    }

}
