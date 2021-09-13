call gradlew shadowJar
call cdk synth --output build/cdk.out --app "java -jar build/libs/aws-iac-java-1.0-SNAPSHOT-all.jar"
call cdk bootstrap aws://131946396884/us-east-1 --output build/cdk.out --app "java -jar build/libs/aws-iac-java-1.0-SNAPSHOT-all.jar"
call cdk deploy --output build/cdk.out --app "java -jar build/libs/aws-iac-java-1.0-SNAPSHOT-all.jar"