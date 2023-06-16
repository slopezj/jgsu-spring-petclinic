pipeline {
    agent any
    stages {
        stage('Checkout') {
            steps {
                // Get some code from a GitHub repository
                git url: 'https://github.com/slopezj/jgsu-spring-petclinic.git', 
                branch: 'main'
            }
        }
        
        stage('Build') {
            steps {             
                bat "mvn clean package"
            }
        
            post {
                    // If Maven was able to run the tests, even if some of the test
                    // failed, record the test results and archive the jar file.
                    //success {
                    always {
                        junit '**/target/surefire-reports/TEST-*.xml'
                        archiveArtifacts 'target/*.jar'
                   }
            }
        }
   }
}
