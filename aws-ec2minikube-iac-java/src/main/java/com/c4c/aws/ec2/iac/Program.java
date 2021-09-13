package com.c4c.aws.ec2.iac;

import com.c4c.aws.ec2.iac.stacks.Ec2Stack;
import software.amazon.awscdk.core.App;

import java.io.IOException;

public class Program {
    public static void main(String [] args) throws IOException {
        System.out.println("Welcome to AWS CDK");
        App app = new App();

        new Ec2Stack(app, "C4C-EC2-CLUSTER-STACK");

        // required until https://github.com/aws/jsii/issues/456 is resolved
        app.synth();

    }
}
