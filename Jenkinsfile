pipeline {
    agent any

    stages {
        stage ('Build Application') {
            steps {
                sh 'export MAVEN_HOME=/var/jenkins_home/maven/apache-maven-3.6.3'
                sh 'export PATH=$PATH:$MAVEN_HOME/bin'
                sh 'mvn --version'
                sh 'mvn clean install'
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
