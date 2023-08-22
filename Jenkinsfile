pipeline{

    agent any
    tools{
        maven 'Maven-3.9.4'
    }
    stages{
        stage("run") {
            steps {
               echo 'executing maven...' 
               sh'./pom.xml -v'
            }
        }
    }
}
