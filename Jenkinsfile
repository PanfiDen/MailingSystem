pipeline{

    agent any

    stages{
        stage("run") {
            steps {
               echo 'executing maven...' 
               sh '''
                    export JAVA_HOME="/lib/jvm/jdk-17"
                    chmod +x mvnw
                    ./mvnw clean package
                '''
            }
        }
    }
}
