# Recipe app

## Description

This is a recipe app that allows users to save recipes. The recipes can be filtered by tags and each recipe has a chat section so that users can discuss recipes and ask questions.
It uses the following technologies:
* Java
* JavaFX
* Spring Boot
* Kafka
* Websockets
* SQL

## Table of Contents (Optional)

- [Installation](#installation)
- [Usage](#usage)
- [Credits](#credits)
- [License](#license)

## Installation

To install this project, follow these steps:
1. Pre-requisites:
    * Java 17
    * MySQL / MariaDB
    * Kafka
    * Maven
2. Installing kafka
   * Download kafka from [here](https://kafka.apache.org/downloads) (binary downloads, Scala version doesn't matter for this project)
   * Extract the downloaded file
   * Open a terminal in the extracted folder
   * Linux / Mac: Start zookeeper: `bin/zookeeper-server-start.sh config/zookeeper.properties`
   * Windows: Start zookeeper: `bin\windows\zookeeper-server-start.bat config\zookeeper.properties`
   * Linux / Mac: Start kafka: `bin/kafka-server-start.sh config/server.properties`
   * Windows: Start kafka: `bin\windows\kafka-server-start.bat config\server.properties`
   1. Extra notes on Kafka:
      * The application will create the topics automatically and use the settings in your kafka config/server.properties file
      * For better scalability and performance, you can create multiple partitions for the topics. This can be done by modifying/adding the following lines to your config/server.properties file:
      * `num.partitions=N` where N is the number of partitions you want to create (default is 1, recommended is 3 or more)
      * You could also run multiple kafka brokers on the same machine by copying the server.properties file and modifying the following lines in your file:
      * `broker.id=N` where N is the broker id, each broker must have a unique id
      * `listeners=PLAINTEXT://:9092` where 9092 is the port number, each broker must have a unique port number
      * `log.dirs=/tmp/kafka-logs-N` where N is the broker id, each broker must have a unique log directory
      * If you do run multiple brokers you must also add them to the application.properties file in both recipe-backend and recipe-frontend in the spring.kafka.bootstrap-servers property, for example: `spring.kafka.bootstrap-servers=localhost:9092,localhost:9093,localhost:9094`
3. Installing MySQL / MariaDB
   * MariaDB is open source and can be downloaded from [here](https://mariadb.org/download/)
   * MySQL is not open source and can be downloaded from [here](https://www.mysql.com/downloads/)
   * Install the database and create a database called `recipe`
   * Add a user, for example `mysql` with password `password` and grant all privileges on the `recipe` database to the user
   * If you want to use a different user or password, you can change the settings in the application.properties file recipe-backend/resources/
   * If you host the database on a different machine, you can change the settings in the application.properties file recipe-backend/resources/
4. Installing / running the app
   * Clone the repository
   * Make sure you have Java 17 installed
   * Make sure you have made the changes to the application.properties file as described in the previous steps
   * Open a terminal in the recipe-backend folder
     * Linux/Mac: Make sure you have the execute permission on the mvnw file: `chmod +x mvnw`
     * Run `./mvnw spring-boot:run`
     * Windows: Run `.\mvnww.cmd spring-boot:run`
   * Open a terminal in the recipe-frontend folder
     * Linux/Mac: Make sure you have the execute permission on the mvnw file: `chmod +x mvnw`
     * Run `./mvnw spring-boot:run`
     * Windows: Run `.\mvnww.cmd spring-boot:run`

## Usage

1. In the recipe-frontend application use the Send-tab to save a recipe in the database
![Send-tab](assets/images/send.png)
2. In the recipe-frontend application use the Get-tab to see the saved recipes, update all recipes by clicking `All recipes`, you can filter recipes by tags at the top of the window.
![Get-tab](assets/images/get.png)
3. Click on View recipe to see the recipe details and chat with other users about the recipe
![View recipe](assets/images/fullrecipe.png)

## Credits

List your collaborators, if any, with links to their GitHub profiles.
* [member 1](https://github.com/person1)
* [member 2](https://github.com/person1)

## Dependencies
See the pom.xml files in the recipe-backend and recipe-frontend folders for the dependencies

## Tests

The tests can be found in the recipe-backend/src/test/java/org/koffa/recipebackend/ folder

[Test report](htmlReport/index.html)

---

![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg) ![java](https://img.shields.io/github/languages/top/KoffaRn/recipe-app)
