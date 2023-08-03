# SPRING KAFKA PoC

### WHAT IS THIS?
This is a PoC to explore spring/kafka integration. Thematically this mimics the backend infrastructure a casino has receiving data from the machines on their gaming floor. It is assumed there are many such machines in the casino (slot machines, video poker, etc) all of which take player reward cards. Every bet a player makes is an "Event" that is sent as a kafka message to the MQ. The Spring service receives the message, serializes the event to a postgres database, then sends an "EventAck" message back to the MQ indicating whether or not the message was received and processed correctly. This way, if something goes wrong the gaming machine that sent the original message has the ability to respond accordingly. 

### FEATURES 
* Because of the high volume of message traffic the spring service needs to process things are handled in a multi-threaded manner. There are currently ten threads listening to the "event" topic. This is easily changed by changing configuration value `spring.kafka.listener.concurrency` in the `application.properties` file.

* The service uses standard HTTP response codes to indicate to the message sender what happened during processing. These standard codes are easily grokked by any system or engineer. 

* The service exposes an http endpoint `/events` that will return the contents of the event db table. 

* The service uses hibernate ORM to communicate with the database. This not only provides protection against SQLi attacks, it also allows the database (currently postgres) to be changed if need be without altering the application code. Lastly it will also ensure all the necessary tables and relations are created when the application is started. 

* The JSON schemas for the kafka messages are automatically generated at build time and serialized to `src/main/resources/jsonschema`. These can be given to any consumer of the serivce to ensure all stakeholders have a shared understanding as to what the API constract is. 

* The project uses docker compose to spin up containers for kafka, zookeeper, and postgres. 


### HOW TO USE?
1. ensure you have docker, docker-compose, maven, and java/19 installed on your workstation.
2. clone this repository to your workstation
3. open a terminal window and from the project root directory execute command `docker-compose up`
    * this will spin up zookeeper, kafka, and postgres 
4. open a new terminal window and from the project root directory execute command `mvn spring-boot:run`
    * this will spin up the spring boot application. On successful application start a test message will automatically be sent to ensure comms with kafka are working and to populate the database with sample data. 
5. open a browser window and navigate to `localhost:8080/events`. You should get a response akin to this:
```
[
  {
    "playerId": "7c4dac8c-a359-421e-a96b-7891b4ab44d3",
    "gameId": "9dd6e184-2d8c-4451-833e-ea71a1f0b026",
    "currencyCode": "USD",
    "bet": 25,
    "payout": 0,
    "timestamp": "2023-08-02T20:35:30.961096678"
  }
]
```

