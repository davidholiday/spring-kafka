package com.davidholiday.controller;


import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.davidholiday.entity.Event;
import com.davidholiday.entity.EventRepository;


@RestController
public class EventResultController {
    
    private final EventRepository repository;

    EventResultController(EventRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/events")
    List<Event> all() {
        return repository.findAll();
    }

}

