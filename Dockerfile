FROM java:8

EXPOSE 8081

ADD vueblog-0.0.1-SNAPSHOT.jar app.jar
RUN bash -c 'touch /app.jar'

ENTRYPOINT ["java", "--add-modules java.xml.bind", "-jar", "/app.jar", "--spring.profiles.active=pro"]