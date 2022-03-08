pipeline {
  agent none

  options {
    timeout time: 1, unit: 'HOURS'
  }

    def mvnHome = tool 'mvn'
    stages {
        stage ('Build Application') {
            steps {
                sh "'${mvnHome}/bin/mvn' clean install"
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
