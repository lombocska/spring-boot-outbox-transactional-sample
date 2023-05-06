# Spring-Boot 3.x integration with Postgres notify/listen mechanism

This example app is intended to be a showcase for integrating with Postgres as a message queue as well.
Spring-Boot 3.x and Spring-Boot-Integration 6.x brought us the possibility to use postgres as a messaging queue withh Postgres LISTTEN/NOTIFY
mechanism. 
With this new feature, we can implement outboxing pattern easily. :)

For the showcase there is a core-api and a listener service. (in docker-compose file, 2 listeners are configured)
Core-API is eligible to save domains to the db and push notifications to the postgres "queues". Spring-integration-jdbc will create
tables in postgres db with the prefix INT_, like INT_CHANNEL_MESSAGE, etc for handling the messaging in the background.
In case of a successfully acked subscription on the consumer side, the message will be deleted from these utility tables.
Listener service is there for subscribe and consume messages. If the consumption is not managed, the message won't be deleted from the tables.

With this setup outbox pattern at least once concept can be implemented for DB aand messaging broker transactions.

## Usage 

- `cd docker`
- Permission: `chmod +x build.sh start.sh stop.sh`
- Build jar files: `./build.sh`
- Run Postgres, Kafka, Core-Api and 2 Listener services: `./start.sh`
- Requests againg Core-API: `curl --location 'localhost:8081/api/cats' \
  --header 'Content-Type: application/json' \
  --data '{
  "name": "Garfield",
  "age": 35
  }'`
- `curl --location 'localhost:8081/api/dogs' \
  --header 'Content-Type: application/json' \
  --data '{
  "name": "Goofy",
  "age": 35
  }''`
- At the end to stop containers: `./stop.sh`


## Tech Stack

- Spring Boot (3.0.6) & Spring Boot Integration JDBC 6.0
- Postgres 13.2
- Spring Kafka
- Docker Compose
- Maven

## Inspirations

