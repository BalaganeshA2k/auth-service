# Project Description

Auth Service is a microservice used for signing up users with email validation and can also get JWT authentication tokens

To be more precise this project can be called as user authentication and authorisation service

Auth service could be deployed to work with api gateways and other microservices of enterprise backends to authorise and authenticate users and other services

> **Disclaimer**
> This project has been made as a prototype/mvp of much more distributed version of the same project. Still this project is usable at some scale. Kindly do few tests before using. I'm doing this project in my spare time.So, all apis are not complete yet. I'll be rolling out apis and test as much as possible.

# Features

- User verification by otp via email
- Configurable email
- Rest Apis for authentication tokens
- In built validation of inputs

# SetUp

- Fork this repo
- Fill the .properties file shown below based on your requirements and add it to the project

```properties
application.security.jwt.secret-key=
application.security.jwt.expiration.day=

user.sign-in.otp.expirtation.seconds=
user.sign-in.otp.length=
password.length.min=
password.length.max=

spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=*****@gmail.com
spring.mail.password=*********
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
spring.mail.properties.mail.transport.protocol=smtp

spring.task.execution.pool.core-size=5
spring.task.execution.pool.max-size=10
spring.task.execution.pool.queue-capacity=25
spring.task.execution.thread-name-prefix=MyAsyncThread-
```

- run the application

## Configuring spring mail

Properites with the below prefix can be used to configure smtp

```
spring.mail.**=
```

Refer the simple [step-by-step](https://www.geeksforgeeks.org/spring-boot-sending-email-via-smtp/) explaination of configuring spring.mail properties with google mail.
