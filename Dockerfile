FROM adoptopenjdk/openjdk11 AS build
ADD . /src
WORKDIR /src
RUN ./gradlew service:clean service:build  service:bootJar

FROM adoptopenjdk:11-jre-hotspot
EXPOSE 9099
ENV DATASOURCE_URL ${DATASOURCE_URL}
ENV DATASOURCE_USERNAME ${DATASOURCE_USERNAME}
ENV DATASOURCE_PASSWORD ${DATASOURCE_PASSWORD}
COPY --from=build /src/service/build/libs/service-*boot.jar /usr/local/bin/service.jar
HEALTHCHECK --retries=12 --interval=10s CMD curl -s localhost:9099/health || exit 1
RUN chmod +x /usr/local/bin/service.jar
ENTRYPOINT ["java", "-jar", "/usr/local/bin/service.jar"]
