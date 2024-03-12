package com.example.Vms.controllers;

import com.example.Vms.entities.Event;
import com.example.Vms.models.EventModel;
import com.example.Vms.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/event")
public class EventController {
    @Autowired
    EventService eventService;
    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody EventModel eventModel){
        System.out.println(eventModel);
        Event event =  eventService.save(eventModel);
        return new ResponseEntity<>(event, HttpStatus.CREATED);
    }
}
