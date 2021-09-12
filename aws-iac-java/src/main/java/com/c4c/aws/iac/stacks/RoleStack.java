package com.c4c.aws.iac.stacks;

import org.jetbrains.annotations.NotNull;
import software.amazon.awscdk.core.Construct;
import software.amazon.awscdk.core.Stack;
import software.amazon.awscdk.core.StackProps;
import software.amazon.awscdk.services.iam.*;

import java.util.ArrayList;
import java.util.List;

public class RoleStack extends Stack {
    public RoleStack(final Construct parent, final String id) {

        this(parent, id, null);
    }
    public RoleStack(final Construct parent, final String id, final StackProps props) {
        super(parent, id, props);
        List<@NotNull IManagedPolicy> policies = new ArrayList<>();
        @NotNull IManagedPolicy iManagedPolicy = ManagedPolicy.fromManagedPolicyArn(this, "c4c-AmazonEKSClusterPolicy", "arn:aws:iam::aws:policy/AmazonEKSClusterPolicy");
        policies.add(iManagedPolicy);

        Role role = Role.Builder.create(this, id)
                .assumedBy(new ServicePrincipal("eks.amazonaws.com"))
                .description("Role to manage EKS cluster")
                .roleName("c4c-eks--cluster-mgmt-role")
                .managedPolicies(policies)
                .build();

    }
}
