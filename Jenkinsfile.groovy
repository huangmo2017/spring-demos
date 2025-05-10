pipeline {
    agent any

    environment {
        // 设置 Docker 和 kubectl 的路径，根据你的Jenkins环境调整
        PATH = "/usr/local/bin:${env.PATH}"
        DOCKER_IMAGE = "test/001-springboot-demo-helloworld:${env.BUILD_NUMBER}"
        //KUBECONFIG = credentials('kubeconfig') // 使用你在Jenkins中保存的KubeConfig凭据ID
    }

    stages {
        // 阶段一：拉取代码
        stage('Checkout') {
            steps {
                git branch: 'master',
                        url: 'https://github.com/huangmo2017/spring-demos.git',
                        credentialsId: 'github-token'  // 替换为你在Jenkins中配置的凭据ID
            }
        }

        // 阶段二：Maven 构建
        stage('Build with Maven') {
            steps {
                withMaven(maven: 'M3') {
                    sh 'mvn clean package -pl 001-springboot-demo-helloworld -am'
                }
            }
        }

        //阶段三：可选 - 展示构建结果或归档文件
        stage('Archive JAR') {
            steps {
                archiveArtifacts artifacts: '001-springboot-demo-helloworld/target/*.jar'
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    docker.build(DOCKER_IMAGE, './001-springboot-demo-helloworld')
                }
            }
        }
    }
}
