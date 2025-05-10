pipeline {
    agent { label 'docker-capable' }

    environment {
        PATH = "/usr/local/bin:${env.PATH}"
        DOCKER_IMAGE = "test/001-springboot-demo-helloworld:${env.BUILD_NUMBER}"
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'master',
                        url: 'https://github.com/huangmo2017/spring-demos.git',
                        credentialsId: 'github-token'
            }
        }

        stage('Build with Maven') {
            steps {
                withMaven(maven: 'M3') {
                    sh 'mvn clean package -pl 001-springboot-demo-helloworld -am'
                }
            }
        }

        stage('Debug - Check Files') {
            steps {
                sh 'ls -la'
                sh 'cat ./001-springboot-demo-helloworld/Dockerfile'
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    docker.build(DOCKER_IMAGE, "-f ./001-springboot-demo-helloworld/Dockerfile .")
                }
            }
        }
    }
}
