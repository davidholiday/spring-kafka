package com.davidholiday;


import java.time.LocalDateTime;
import java.util.UUID;

import org.apache.kafka.clients.admin.NewTopic;
import org.hibernate.dialect.lock.OptimisticEntityLockException;
import org.joda.money.Money;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.http.HttpStatus;

import com.davidholiday.entity.EventRepository;
import com.davidholiday.json.Event;
import com.davidholiday.json.EventAck;
import com.fasterxml.jackson.core.JsonProcessingException;
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

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;


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
        event.setCurrencyCode("USD");
        event.setBet(25.0);
        event.setPayout(0.0);
        event.setTimestamp(LocalDateTime.now().toString());

        return args -> {
            template.send(EVENT_TOPIC, mapper.writeValueAsString(event));
        };
    }


    @KafkaListener(id = "eventConsumer", topics = EVENT_TOPIC)
    public void listen(String in) throws Exception {
        LOG.info("received event message: {}", in);
        ObjectMapper mapper = new ObjectMapper();
        Event eventJson = new Event();
        EventAck eventAck = new EventAck();

        try { 
            eventJson = mapper.readValue(in, Event.class);
            com.davidholiday.entity.Event eventJpa = new com.davidholiday.entity.Event(eventJson);
            eventAck.setGameId(eventJson.getGameId());
            eventAck.setPlayerId(eventJson.getPlayerId());

            LOG.info("serializing event to db...");
            this.repository.save(eventJpa);

            eventAck.setResponseCode(HttpStatus.OK);
            LOG.info("sending event ack message...");
            kafkaTemplate.send(EVENT_ACK_TOPIC, mapper.writeValueAsString(eventAck));

        } catch (JsonProcessingException e) {
            LOG.error("something went wrong processing inbound event message", e);
            eventAck.setResponseCode(HttpStatus.BAD_REQUEST);
            kafkaTemplate.send(EVENT_ACK_TOPIC, mapper.writeValueAsString(eventAck));
        } catch (IllegalArgumentException | OptimisticLockingFailureException  ee) {
            LOG.error("something went wrong serializing event to db", ee);
            eventAck.setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR);
            kafkaTemplate.send(EVENT_ACK_TOPIC, mapper.writeValueAsString(eventAck));
        }

    }


}
