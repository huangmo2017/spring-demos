# 使用官方的 OpenJDK 作为基础镜像
FROM openjdk:17-jdk-alpine

# 设置工作目录
WORKDIR /app

# 将构建好的 jar 包添加到容器中
COPY ./target/001-springboot-demo-helloworld.jar /app/app.jar

# 暴露应用程序运行的端口（根据实际情况调整）
EXPOSE 9090

# 定义启动命令
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
