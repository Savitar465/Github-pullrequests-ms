FROM public.ecr.aws/docker/library/eclipse-temurin:21-jdk-alpine

LABEL org.opencontainers.image.title="Github-pullrequest-ms" \
	org.opencontainers.image.description="Microservicio de pull requets" \
	org.opencontainers.image.vendor="Githubx" \
	org.opencontainers.image.url="https://github.com/Savitar465/Github-pullrequests-ms" \
	org.opencontainers.image.source="https://github.com/Savitar465/Github-pullrequests-ms" \
	org.opencontainers.image.documentation="https://github.com/Savitar465/Github-pullrequests-ms/blob/main/README.md" \
	org.opencontainers.image.authors="Savitar465"
EXPOSE 8084
USER root

COPY target/Github-pullrequests-ms.jar Github-pullrequests-ms.jar
ENTRYPOINT ["java","-jar","/Github-pullrequests-ms.jar"]