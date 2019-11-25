FROM gradle:jdk11 as build-stage
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build

FROM gradle:jre11
COPY --from=build-stage /home/gradle/src /home/gradle/src
WORKDIR /home/gradle/src
CMD ["gradle", "--stacktrace", "run"]