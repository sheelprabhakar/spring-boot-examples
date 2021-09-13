package com.c4c.aws.iac;

import com.c4c.aws.iac.stacks.EksClusterStack;
import com.c4c.aws.iac.stacks.RoleStack;
import software.amazon.awscdk.core.App;

public class Program {
    public static void main(String [] args){
        System.out.println("Welcome to AWS CDK");
        App app = new App();

        new EksClusterStack(app, "C4C-EKS-CLUSTER-STACK");

        // required until https://github.com/aws/jsii/issues/456 is resolved
        app.synth();
    }
}
