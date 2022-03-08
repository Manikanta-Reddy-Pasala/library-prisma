pipeline {

  agent any
  
  options {
    timeout time: 1, unit: 'HOURS'
  }

    stages {
        stage ('Build Application') {
            steps {
                sh 'echo $JAVA_HOME'
                sh 'java -version'
                sh 'mvn clean package'
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
