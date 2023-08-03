# Mailing System Application
Mailing System Application is a web program that allows managing email distribution through an API and 
automated sending using pre-defined time-based rules. Users can add recipients they wish to include 
in message distribution, such as promotional emails, reminders, etc. They can edit recipients information, 
view the list of recipients, delete recipients, set up scheduled delivery rules, send emails to the recipients, 
perform automated sending to all available recipients through cron jobs, and also review information about sent emails.

## How to Run the Project

To run the project locally, follow these steps:

#### Running with JDK:

1. Clone the repository to your local machine: ``` git clone https://github.com/PanfiDen/MailingSystem```
2. Make sure you have Java and Maven installed.
3. To ensure the proper functioning of the application, you need to set the following Environment variables
   (These variables should be configured with appropriate values, such as the actual Gmail account and 
    its corresponding password, to enable the application to function correctly.):
- EMAIL=ExampleGmailAccount;
- PASSWORD=ExamplePassword.
4. Open a terminal and navigate to the project's root directory.
5. Run the following command to build the project: `mvn clean install`.
6. Once the build is successful, run the following command to start the application: `mvn spring-boot:run`.
7. The application will start running on `http://localhost:8080`.
8. Access the application using a Postman or any other util.

#### Using Docker:

1. Make sure you have Docker installed on your system.
2. Clone this repository to your local machine.
3. Open a terminal or command prompt and navigate to the project's root directory.
4. Build the Docker image using the provided Dockerfile: `docker build -t my-spring-boot-app .`.
5. Once the Docker image is built successfully, you can run the Spring Boot application inside 
   a Docker container with the following command: `docker run -p 8080:8080 my-spring-boot-app`.
6. The application should now be accessible at `http://localhost:8080` in your web browser.
7. Access the application using a Postman or any other util.

## Swagger
After launching the project, by navigating to the link http://localhost:8080/swagger-ui.html, you can access all 
the endpoints available in the application, which will assist you in navigating through the project.

## Technology
- Java 11+, 
- Spring Boot, 
- H2 database, 
- Gmail SMTP server
- Swagger
- Docker.
