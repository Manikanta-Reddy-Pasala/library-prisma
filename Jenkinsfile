pipeline {
    agent any
    options {
        disableConcurrentBuilds()
        buildDiscarder(logRotator(numToKeepStr: '10'))
    }
    parameters {
        // booleanParam, choice, file, text, password, run, or string
        booleanParam(defaultValue: false, description: 'Do you want to deploy the application?', name: 'deployment')
        //string(defaultValue: "TEST", description: 'What environment?', name: 'stringExample')
        //text(defaultValue: "This is a multiline\n text", description: "Multiline Text", name: "textExample")
        //choice(choices: 'US-EAST-1\nUS-WEST-2', description: 'What AWS region?', name: 'choiceExample')
        //choice(choices: ['greeting' , 'silence'],description: '', name: 'REQUESTED_ACTION')
        //password(defaultValue: "Password", description: "Password Parameter", name: "passwordExample")
    }

    environment {
        //Use Pipeline Utility Steps plugin to read information from pom.xml into environment variables
        ARTIFACT_ID = readMavenPom().getArtifactId()
        ARTIFACT_VERSION = readMavenPom().getVersion()
        DEPLOYMENT= "${params.deployment}"
    }

    stages {
        stage ('Build Application') {
            steps {
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
