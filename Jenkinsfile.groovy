pipeline {
    agent { label 'docker-capable' }

    environment {
        PATH = "/usr/local/bin:${env.PATH}"
        DOCKER_IMAGE = "huangmo2017/001-springboot-demo-helloworld:${env.BUILD_NUMBER}"
        DOCKER_CREDENTIALS_ID = 'docker_hub_huangmo2017' // 确保已在Jenkins中添加了Docker Hub的凭证
        KUBECONFIG = credentials('k8s-config') // 引用之前设置的kubeconfig凭证
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

        stage('Push Docker Image') {
            steps {
                script {
                    docker.withRegistry('https://registry.hub.docker.com', DOCKER_CREDENTIALS_ID) {
                        //docker.image(DOCKER_IMAGE).push()
                    }
                }
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                steps {
                    sh """
                    kubectl apply -f helloworld-deployment.yaml
                    kubectl apply -f helloworld-service.yaml
                    """
                }
            }
        }
    }
}
