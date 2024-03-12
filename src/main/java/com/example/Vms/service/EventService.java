package com.example.Vms.service;

import com.example.Vms.entities.Event;
import com.example.Vms.models.EventModel;
import com.example.Vms.repositories.EventRepo;
import com.example.Vms.repositories.OrganisationRepo;
import com.example.Vms.repositories.VolunteerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventService {
    @Autowired
    EventRepo eventRepo;
    @Autowired
    OrganisationRepo organisationRepo;
    @Autowired
    VolunteerRepo volunteerRepo;
    public Event save(EventModel eventModel){
        Event event = new Event(eventModel);
        System.out.println(event);
        return eventRepo.save(event);
    }
}
