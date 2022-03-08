node {

    def mvnHome = tool 'maven-3.8.4'
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
