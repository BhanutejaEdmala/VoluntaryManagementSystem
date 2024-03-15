package com.example.Vms.controllers;

import com.example.Vms.entities.Event;
import com.example.Vms.models.EventModel;
import com.example.Vms.service.EventService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/event")
public class EventController {
    @Autowired
    EventService eventService;
    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody @Valid EventModel eventModel){
        System.out.println(eventModel);
        Event event =  eventService.save(eventModel);
        return new ResponseEntity<>(event, HttpStatus.CREATED);
    }
    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestParam int eid){
        String result = eventService.deleteEvent(eid);
        return ResponseEntity.ok(result);
    }
    @PatchMapping("/update")
    public ResponseEntity<?> updateEvent(@RequestBody Event event,@RequestParam int eid){
        Event event1 = eventService.updateEvent(event,eid);
        return ResponseEntity.ok(event1);
    }
    @GetMapping("/get")
    public ResponseEntity<?> get(@RequestParam int eid){
        Event event = eventService.get(eid);
        if(event!=null)
            return new ResponseEntity<>(event,HttpStatus.FOUND);
        return new ResponseEntity<>("No Event Found",HttpStatus.NOT_FOUND);
    }
 }
