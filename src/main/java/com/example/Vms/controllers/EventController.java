package com.example.Vms.controllers;

import com.example.Vms.entities.Event;
import com.example.Vms.models.EventModel;
import com.example.Vms.service.serviceimplementationss.EventService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/event")
public class EventController {
    @Autowired
    EventService eventService;
    @PostMapping("/saveevent")
    public ResponseEntity<?> save(@RequestBody @Valid EventModel eventModel){
        System.out.println(eventModel);
        EventModel event =  eventService.save(eventModel);
        return new ResponseEntity<>(event, HttpStatus.CREATED);
    }
    @DeleteMapping("/deleteevent")
    public ResponseEntity<?> delete(@RequestParam int eventId){
        String result = eventService.deleteEvent(eventId);
        return ResponseEntity.ok(result);
    }
    @PatchMapping("/updateevent")
    public ResponseEntity<?> updateEvent(@RequestBody  Event event,@RequestParam int eventId){
        String result = eventService.updateEvent(event,eventId);
        return ResponseEntity.ok(result);
    }
    @GetMapping("/getevent")
    public ResponseEntity<?> get(@RequestParam int eventId){
        EventModel event = eventService.get(eventId);
        if(event!=null)
            return new ResponseEntity<>(event,HttpStatus.FOUND);
        return new ResponseEntity<>("No Event Found",HttpStatus.NOT_FOUND);
    }
 }
