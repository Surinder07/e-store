pipeline {
    agent any

    tools {
        maven "Maven 3.8.5"
    }

    stages {
        stage('Compile and Clean') {
            steps {
                sh "mvn clean compile"
            }
        }
        stage('Deploy') {
            steps {
                sh "mvn package"
            }
        }
        stage('Build Docker image') {
            steps {
                script {
                    echo "hello e store"
                    sh 'ls'

                    // Build the Docker image with logging
                    def dockerImage = docker.build("surinder0322/e-store:${BUILD_NUMBER}", ".")

                    // Capture Docker logs
                    def logs = dockerImage.inside {
                        sh 'echo "Docker image build successful!"'
                    }

                    // Print Docker logs
                    println "Docker Build Logs:"
                    println logs
                }
            }
        }
        stage('Docker Login') {
            steps {
                withCredentials([string(credentialsId: 'DockerId', variable: 'Dockerpwd')]) {
                    // Log in to Docker registry
                    docker.withRegistry('https://registry.example.com', 'docker-credentials') {
                        sh "docker login -u surinder0322 -p ${Dockerpwd}"
                    }
                }
            }
        }
        stage('Docker Push') {
            steps {
                sh 'docker push surinder0322/e-store:${BUILD_NUMBER}'
            }
        }
        stage('Docker Deploy') {
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
