currentBuild.displayName = ""
pipeline {
    agent any

    tools {
        // Specify the Maven installation name as configured in Jenkins
        maven "Maven 3.8.5"
    }

    stages {
        stage('Compile and Clean') {
            steps {
                // Run Maven on a Unix agent.
                sh "mvn clean compile"
            }
        }
        stage('deploy') {
            steps {
                sh "mvn package"
            }
        }
        stage('Build Docker image') {
            steps {
                echo "hello e store"
                sh 'ls'
                sh 'docker build -t surinder0322/e-store:${BUILD_NUMBER} .'
            }
        }
        stage('Docker Login') {
            steps {
                withCredentials([string(credentialsId: 'DockerId', variable: 'Dockerpwd')]) {
                    sh "docker login -u surinder0322 -p ${Dockerpwd}"
                }
            }
        }
        stage('Docker Push') {
            steps {
                sh 'docker push surinder0322/e-store:${BUILD_NUMBER}'
            }
        }
        stage('Stop Existing Docker Container') {
            steps {
                script {
                    // Find the container ID using port 9092
                    def existingContainerId = sh(script: 'docker ps -q -f "expose=9092"', returnStatus: true).trim()

                    // Stop and remove the existing container if it's found
                    if (existingContainerId) {
                        sh "docker stop ${existingContainerId}"
                        sh "docker rm ${existingContainerId}"
                    } else {
                        echo "No existing container found using port 9092."
                    }
                }
            }
        }
        stage('Docker deploy') {
            steps {
                sh 'docker run -itd -p 9092:8080 surinder0322/e-store:${BUILD_NUMBER}'
            }
        }
        stage('Archiving') {
            steps {
                archiveArtifacts '**/target/*.jar'
            }
        }
    }
}
