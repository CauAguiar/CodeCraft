# Codecraft

Codecraft is an interactive, gamified learning platform inspired by Duolingo, designed to teach programming in a fun and practical way. The project consists of two main components: the mobile app (front-end), developed using Kotlin and Java, and the backend, built with Spring Boot.

## Features

- **Interactive Learning**: We teach programming through practical and challenging exercises.
- **Gamification**: Like Duolingo, Codecraft uses gamification mechanics such as points, levels, and achievements.
- **Support for Multiple Languages**: Users can learn various programming languages, with up-to-date and progressive content.
- **Personalized Progression**: The system adapts challenges based on the user's progress, keeping learning dynamic and effective.

## Technologies Used

### Front-End (Mobile)
- **Kotlin**: Used for developing the Android app, providing a smooth and modern experience.
- **Java**: Used alongside Kotlin to support legacy features and ensure compatibility with older Android versions.

### Back-End
- **Spring Boot**: Framework used to build the RESTful API, responsible for managing server logic, interacting with the database, and providing data to the front-end.

### Database
- **MySQL**: Relational database used to store user information, progress, and content.

## How to Run the Project

### Backend (Spring Boot)

1. Clone this repository:
    ```bash
    git clone https://github.com/CauAguiar/codecraft.git
    cd spirgboot
    ```

2. Configure the MySQL database with the appropriate credentials in the `application.properties` file:
    ```properties
    spring.datasource.url=jdbc:mysql://localhost:3306/codecraft_db
    spring.datasource.username=root
    spring.datasource.password=your_password
    spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
    spring.jpa.hibernate.ddl-auto=update
    spring.jpa.database-platform=org.hibernate.dialect.MySQL5Dialect
    ```

3. Run the application:
    ```bash
    ./mvnw spring-boot:run
    ```

4. The API will be available at `http://localhost:8080`.

### Front-End (Mobile)

1. Clone this repository:
    ```bash
    git clone https://github.com/CauAguiar/codecraft.git
    cd tentativarestic
    ```

2. Open the project in Android Studio and run it on an emulator or physical device.

### Explanation of Main Directories:

- **spirgboot/**
  - **src/**: Contains the Java code for the backend. It is structured with `controllers`, `services`, `repositories`, and `models` to organize your application.
  - **pom.xml**: The Maven configuration file for managing dependencies and building the backend project.
  - **application.properties**: The Spring Boot configuration file for setting up the backend server and database connections.

- **tentativarestic/**
  - **app/**: The Android mobile app source code. It includes activities, fragments, and adapters used in the UI. The code is written in Kotlin or Java.
  - **res/**: Resources used in the app, such as layouts, images, and strings.
  - **build.gradle**: The Gradle configuration file for the mobile project, managing dependencies and build tasks.
  - **AndroidManifest.xml**: The configuration file for the Android app that defines the app’s components and permissions.




