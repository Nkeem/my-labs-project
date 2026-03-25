FROM bellsoft/liberica-runtime-container:jre-21-slim-glibc
WORKDIR /app
COPY target/lab2-*-fat.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
