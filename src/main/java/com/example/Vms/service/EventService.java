package com.example.Vms.service;

import com.example.Vms.entities.Event;
import com.example.Vms.entities.Organisation;
import com.example.Vms.entities.Volunteer;
import com.example.Vms.models.EventModel;
import com.example.Vms.repositories.EventRepo;
import com.example.Vms.repositories.OrganisationRepo;
import com.example.Vms.repositories.VolunteerRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public String deleteEvent(int eid){
        Event event = eventRepo.findById(eid).orElse(null);
        if(event!=null){
            List<Organisation> organisations = organisationRepo.findAll();
            List<Volunteer> volunteers = volunteerRepo.findAll();
            volunteers.stream().filter(i->i.getEvents().contains(event)).forEach(i->i.getEvents().remove(event));
            organisations.stream().filter(i->i.getEvents().contains(event)).forEach(i->i.getEvents().remove(event));
            volunteerRepo.saveAll(volunteers);
            organisationRepo.saveAll(organisations);
            eventRepo.delete(event);
            return "Deleted";
        }
        return "Event Doesn't Exist";
    }
    public Event updateEvent(Event event,int eid){
        Event event1 = eventRepo.findById(eid).orElse(null);
        if(event1!=null){
           event1.setName(event.getName());
           event1.setDate(event.getDate());
           event1.setLocation(event.getLocation());
           event1.getSkills_good_to_have().addAll(event.getSkills_good_to_have());
           eventRepo.save(event1);
        }
        return null;
    }
    public Event get(int eid){
        Event event = eventRepo.findById(eid).orElse(null);
        if(event!=null)
            return event;
        return null;
    }
}
