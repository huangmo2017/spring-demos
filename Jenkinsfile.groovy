pipeline {
    agent { label 'docker-capable' }

    environment {
        // 设置 Docker 和 kubectl 的路径，根据你的Jenkins环境调整
        PATH = "/usr/local/bin:${env.PATH}"
        DOCKER_IMAGE = "test/001-springboot-demo-helloworld:${env.BUILD_NUMBER}"
        //KUBECONFIG = credentials('kubeconfig') // 使用你在Jenkins中保存的KubeConfig凭据ID
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'master',
                        url: 'https://github.com/huangmo2017/spring-demos.git',
                        credentialsId: 'github-token'  // 替换为你在Jenkins中配置的凭据ID
            }
        }

        stage('Build with Maven') {
            steps {
                withMaven(maven: 'M3') {
                    sh 'mvn clean package -pl 001-springboot-demo-helloworld -am'
                }
            }
        }

        stage('Build Docker Image') {
            agent { label 'docker-capable' }
            steps {
                script {
                    // 指定 Dockerfile 的路径和构建上下文路径
                    docker.build(DOCKER_IMAGE, "-f ./001-springboot-demo-helloworld/Dockerfile ./001-springboot-demo-helloworld")
                }
            }
        }
    }
}
