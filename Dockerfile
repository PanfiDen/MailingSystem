FROM openjdk
COPY target/MailingSystem-0.0.1-SNAPSHOT.jar /MailingSystem-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "MailingSystem-0.0.1-SNAPSHOT.jar"]
