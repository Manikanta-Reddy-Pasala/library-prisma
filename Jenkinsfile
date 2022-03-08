pipeline {

  agent any
  
  options {
    timeout time: 1, unit: 'HOURS'
  }

    stages {
        stage ('Build Application') {
            steps {
                sh 'java -version'
                sh 'ls -ltr'
                sh './mvnw clean package'
            }
            post {
                success {
                    junit 'target/surefire-reports/**/*.xml'
                }
                failure {
                    sh 'echo Build failed, Sending notification....'
                    // logic to send notification
                }
            }
        }



    }
}
