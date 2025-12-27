FROM ubuntu:20.04

ENV DEBIAN_FRONTEND=noninteractive

RUN apt-get update && \
    apt-get install -y --no-install-recommends \
        openjdk-17-jdk \
        maven \
        ca-certificates \
        tzdata && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

WORKDIR /app

COPY . .

RUN mvn -B -DskipTests clean package

EXPOSE 8081

RUN /bin/bash -lc 'cp target/*.jar /app/app.jar'

CMD ["java", "-jar", "/app/app.jar", "--server.port=8081"]
