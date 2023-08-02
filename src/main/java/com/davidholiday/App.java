package com.davidholiday;


import java.util.UUID;

import org.apache.kafka.clients.admin.NewTopic;

import org.joda.money.Money;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.http.HttpStatus;

import com.davidholiday.entity.EventRepository;
import com.davidholiday.json.Event;
import com.davidholiday.json.EventAck;
import com.fasterxml.jackson.databind.ObjectMapper;


@SpringBootApplication
public class App {

    private static Logger LOG = LoggerFactory.getLogger(App.class);

    public static final String EVENT_TOPIC = "event";

    public static final String EVENT_ACK_TOPIC = "event_ack";

    public static void main( String[] args ) {
        SpringApplication.run(App.class, args);
    }


    @Autowired
    private EventRepository repository;


    @Bean
    public NewTopic serializeTopic() {
        return TopicBuilder.name(EVENT_TOPIC)
                           // *!* you need at least as many partitions as you have consumers
                           // check "spring.kafka.listener.concurrency" in file application.properties 
                           .partitions(10)
                           .replicas(1)
                           .build();
    }


    @Bean
    public NewTopic serializeAckTopic() {
        return TopicBuilder.name(EVENT_ACK_TOPIC)
                           // *!* you need at least as many partitions as you have consumers
                           // check "spring.kafka.listener.concurrency" in file application.properties 
                           .partitions(10)
                           .replicas(1)
                           .build();
    }


    @Bean
    public ApplicationRunner runner(KafkaTemplate<String, String> template) {
        ObjectMapper mapper = new ObjectMapper();

        var event = new Event();
        event.setPlayerId(UUID.randomUUID().toString());
        event.setGameId(UUID.randomUUID().toString());
        event.setBet(Money.parse("USD 25.0"));
        event.setPayout(Money.parse("USD 0.0"));


        var eventAck = new EventAck();
        eventAck.setPlayerId(event.getPlayerId());
        eventAck.setGameId(event.getGameId());
        eventAck.setResponseCode(HttpStatus.OK);

        return args -> {
            template.send(EVENT_ACK_TOPIC, mapper.writeValueAsString(eventAck));
        };
    }


    @KafkaListener(id = "myId", topics = EVENT_ACK_TOPIC)
    public void listen(String in) {
        LOG.info("received ack message: {}", in);
    }


}
