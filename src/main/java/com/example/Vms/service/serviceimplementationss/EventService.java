package com.example.Vms.service.serviceimplementationss;

import com.example.Vms.conversions.EntityToModel;
import com.example.Vms.conversions.ModelToEntity;
import com.example.Vms.entities.Event;
import com.example.Vms.entities.Organisation;
import com.example.Vms.entities.Volunteer;
import com.example.Vms.models.EventModel;
import com.example.Vms.repositories.EventRepo;
import com.example.Vms.repositories.OrganisationRepo;
import com.example.Vms.repositories.VolunteerRepo;
import com.example.Vms.service.serviceinterfaces.EventServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class EventService implements EventServiceInterface {
    @Autowired
    EventRepo eventRepo;
    @Autowired
    OrganisationRepo organisationRepo;
    @Autowired
    VolunteerRepo volunteerRepo;
    @Autowired
    ModelToEntity modelToEntity;
    @Autowired
    EntityToModel entityToModel;
    public EventModel save(EventModel eventModel){
       Event event = modelToEntity.EventModelToEvent(eventModel);
       eventRepo.save(event);
        return eventModel;
    }
    public String deleteEvent(int eventId){
        Event event = eventRepo.findById(eventId).orElse(null);
        if(null!=event){
            List<Organisation> organisations = organisationRepo.findAll();
            List<Volunteer> volunteers = volunteerRepo.findAll();
            volunteers.stream().filter(i->i.getEvents().contains(event)).forEach(i->i.getEvents().remove(event));
            organisations.stream().filter(i->i.getEvents().contains(event)).forEach(i->i.getEvents().remove(event));
            volunteerRepo.saveAll(volunteers);
            organisationRepo.saveAll(organisations);
            eventRepo.delete(event);
            return "Event Deleted";
        }
        return "Event Doesn't Exist";
    }
    public String updateEvent(Event event,int eventId){
        Event event1 = eventRepo.findById(eventId).orElse(null);
        if(event1!=null&&!(CollectionUtils.isEmpty(event1.getVolunteerList())))
             return "This Event Is Already Assigned To Few Volunteers , Updating It Might Lead To Confusion";
        if(null!=event1){
           event1.setName(event.getName());
           event1.setDate(event.getDate());
           event1.setLocation(event.getLocation());
           event1.setTimings(event.getTimings());
           event1.setStatus(event.getStatus());
           event1.getSkills_good_to_have().addAll(event.getSkills_good_to_have());
           eventRepo.save(event1);
           return "Event updated";
        }
        return null;
    }
    public EventModel get(int eventId){
        Event event = eventRepo.findById(eventId).orElse(null);
        return null!=event ? entityToModel.eventToEventModel(event) : null;
    }
}
