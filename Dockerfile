FROM openjdk:17-jdk-slim

WORKDIR /app

COPY build/libs/DatingChatService-0.0.1-SNAPSHOT.jar ./

CMD ["java", "-jar", "DatingChatService-0.0.1-SNAPSHOT.jar"]