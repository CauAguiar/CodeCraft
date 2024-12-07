# Codecraft

Codecraft is an interactive, gamified learning platform inspired by Duolingo, designed to teach programming in a fun and practical way. The project consists of two main components: the mobile app, developed using Kotlin and Java, and the backend, built with Spring Boot.

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
- **PostgreSQL**: Relational database used to store user information, progress, and content.

## How to Run the Project

### Backend (Spring Boot)

1. Clone this repository:
    ```bash
    git clone https://github.com/yourusername/codecraft.git
    cd backend
    ```

2. Configure the PostgreSQL database with the appropriate credentials in the `application.properties` file.

3. Run the application:
    ```bash
    ./mvnw spring-boot:run
    ```

4. The API will be available at `http://localhost:8080`.

### Front-End (Mobile)

1. Clone this repository:
    ```bash
    git clone https://github.com/yourusername/codecraft.git
    cd mobile
    ```

2. Open the project in Android Studio and run it on an emulator or physical device.

## Directory Structure



