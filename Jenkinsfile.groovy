pipeline {
    agent { label 'k8s-node' }

    environment {
        PATH = "/usr/local/bin:${env.PATH}"
        DOCKER_IMAGE = "192.168.42.113:5000/001-springboot-demo-helloworld:${env.BUILD_NUMBER}"
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
                    docker.withRegistry('http://192.168.42.113:5000/v2/', DOCKER_CREDENTIALS_ID) {
                        docker.image(DOCKER_IMAGE).push()
                    }
                }
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
/*                sh """
                    kubectl apply -f ./001-springboot-demo-helloworld/k8s/deployment.yaml
                    kubectl apply -f ./001-springboot-demo-helloworld/k8s/service.yaml
                    """*/

                sh """
                    export BUILD_NUMBER=${env.BUILD_NUMBER}
                    envsubst < ./001-springboot-demo-helloworld/k8s/deployment.yaml > ./001-springboot-demo-helloworld/k8s/deployment-generated.yaml
                    kubectl apply -f ./001-springboot-demo-helloworld/k8s/deployment-generated.yaml
                    kubectl apply -f ./001-springboot-demo-helloworld/k8s/service.yaml
                    """
            }
        }
    }
}
