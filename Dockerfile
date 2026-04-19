# 使用 Maven 官方镜像进行构建
FROM maven:3.9-eclipse-temurin-17 AS builder

# 设置工作目录
WORKDIR /build

# 复制 pom.xml 并下载依赖（利用 Docker 缓存层）
COPY jiayun01/pom.xml .
RUN mvn dependency:go-offline -B

# 复制源代码
COPY jiayun01/src ./src

# 打包项目（跳过测试以加快构建速度）
RUN mvn package -DskipTests -B

# 使用轻量级运行时镜像
FROM eclipse-temurin:17-jre-alpine

# 设置工作目录
WORKDIR /app

# 从构建阶段复制 jar 包
COPY --from=builder /build/target/chaoxi-gym-1.0.jar app.jar

# 暴露应用端口
EXPOSE 8081

# JVM 参数优化
ENV JAVA_OPTS="-Xms256m -Xmx512m -Djava.security.egd=file:/dev/./urandom"

# 启动应用
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar --spring.profiles.active=pro"]
