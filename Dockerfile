# Stage 1: build the executable jar
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /build
COPY maven-settings.xml /root/.m2/settings.xml
COPY . .
RUN --mount=type=cache,target=/root/.m2/repository \
    mvn -B -DskipTests -pl maku-server -am package \
    && cp maku-server/target/maku-server.jar /build/app.jar

# Stage 2: runtime image
FROM eclipse-temurin:17-jre
WORKDIR /app
ENV TZ=Asia/Shanghai
COPY --from=build /build/app.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-server", "-Xms512M", "-Xmx1024M", "-Djava.security.egd=file:/dev/./urandom", "-Dfile.encoding=UTF-8", "-XX:+HeapDumpOnOutOfMemoryError", "-jar", "/app/app.jar"]
