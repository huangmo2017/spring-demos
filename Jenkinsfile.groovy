pipeline {
    agent any
    stages {
        stage('Checkout') {
            steps {
                git branch: 'main',
                        url: 'https://github.com/huangmo2017/spring-demos.git',
                        credentialsId: 'github-token'  // 这里填写你在 Jenkins 中设置的凭据 ID
            }
        }
    }
}
