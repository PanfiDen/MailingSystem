pipeline{

    agent any

    stages{
        stage("run") {
            steps {
               echo 'executing maven...' 
               sh './mvnw clean package'
            }
        }
    }
}
