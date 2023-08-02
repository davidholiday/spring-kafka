package com.davidholiday;


import org.apache.kafka.clients.admin.NewTopic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;


@SpringBootApplication
public class App {

    Logger LOG = LoggerFactory.getLogger(App.class);

    public static void main( String[] args ) {
        SpringApplication.run(App.class, args);
    }


    @Bean
    public NewTopic serializeTopic() {
        return TopicBuilder.name("serialize")
                           // *!* you need at least as many partitions as you have consumers
                           // check "spring.kafka.listener.concurrency" in file application.properties 
                           .partitions(10)
                           .replicas(1)
                           .build();
    }


    @Bean
    public NewTopic serializeAckTopic() {
        return TopicBuilder.name("serialize_ack")
                           // *!* you need at least as many partitions as you have consumers
                           // check "spring.kafka.listener.concurrency" in file application.properties 
                           .partitions(10)
                           .replicas(1)
                           .build();
    }


    @Bean
    public ApplicationRunner runner(KafkaTemplate<String, String> template) {
        return args -> {
            template.send("serialize_ack", "test");
        };
    }


    @KafkaListener(id = "myId", topics = "serialize_ack")
    public void listen(String in) {
        LOG.info("received ack message: {}", in);
    }


}
