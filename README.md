# senlabookstoreBACKEND
Backend for bookstore task, JAVA
This was configuration for backend.
Application was configured to communicate with postgresql repository.
Fronted part will be done with Angular

This backend part must be run on port 8080

in postgresql you need to create table book

Application properties 
spring.datasource.url=jdbc:postgresql://localhost:5432/book
spring.datasource.username=postgres
spring.datasource.password=Mak2531!
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.properties.hibernate.format_sql=true

Post xml can be done only by postman 
http://localhost:8080/postxml

Trying to fix bug with downloading(download new file only after refresh)
