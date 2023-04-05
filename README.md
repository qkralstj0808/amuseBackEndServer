# amuseBackEndServer

## 1. 개발환경
- Spring Boot 2.3.4
- Java 11
- Gradle 6.7.1
- MySQL 8.0.23
- IntelliJ IDEA 2020.3.2
- Lombok 1.18.20
- Spring Data JPA
- Spring Web
- Spring Boot DevTools
- 


## 2. DB 설정

### application.properties
    spring.datasource.url=jdbc:mysql://localhost:3306/amuse?characterEncoding=UTF-8&serverTimezone=UTC
    spring.datasource.username=root
    spring.datasource.password=khu1479
    datasource.driver-class-name=com.mysql.cj.jdbc.Driver
    spring.jpa.show-sql=true
    spring.jpa.hibernate.ddl-auto=create-drop
    spring.jpa.properties.hibernate.format_sql=true
    logging.level.root=INFO

#### 각자 환경에 맞게 설정

# 폴더 구성

controller              
service                 
domain              
repository              
exception               
dao             
dto             
    - request               
    - response                  
    

