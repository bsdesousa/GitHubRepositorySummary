FROM stakater/oracle-jdk:8u152-alpine-3.7
COPY ./target/GitHubRespositorySummary.jar /
WORKDIR /
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "GitHubRespositorySummary.jar"]

