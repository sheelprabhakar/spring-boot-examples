call gradlew shadowJar
call cdk synth --output build/cdk.out --app "java -jar build/libs/aws-iac-java-1.0-SNAPSHOT-all.jar"
rem call cdk deploy --output build/cdk.out --app "java -jar build/libs/aws-iac-java-1.0-SNAPSHOT-all.jar"