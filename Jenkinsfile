pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                // Check out your source code from your version control system (e.g., Git)
                checkout scm
            }
        }

        stage('Build') {
            steps {
                // Build your Java application using Maven
                sh 'mvn clean package'
            }
        }

        stage('Test') {
            steps {
                // Run tests for your Java application
                sh 'mvn test'
            }
        }
    }

    post {
        success {
            // This block runs if the pipeline is successful
            echo 'Build and test succeeded! Deploy your application here.'
        }
        failure {
            // This block runs if the pipeline fails
            echo 'Build or test failed. Investigate and fix the issues.'
        }
    }
}
