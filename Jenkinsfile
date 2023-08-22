pipeline{

    agent any

    stages{
        stage("run") {
            steps {
               echo 'executing maven...' 
               sh '''
                    chmod +x mvnw
                    ./mvnw clean package
                '''
            }
        }
    }
}
