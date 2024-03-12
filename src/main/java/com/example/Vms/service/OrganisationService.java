package com.example.Vms.service;

import com.example.Vms.entities.Event;
import com.example.Vms.entities.Organisation;
import com.example.Vms.entities.User;
import com.example.Vms.entities.Volunteer;
import com.example.Vms.models.EventModel;
import com.example.Vms.models.OrganisationModel;
import com.example.Vms.models.VolunteerModel;
import com.example.Vms.repositories.EventRepo;
import com.example.Vms.repositories.OrganisationRepo;
import com.example.Vms.repositories.UserRepo;
import com.example.Vms.repositories.VolunteerRepo;
import org.antlr.v4.runtime.atn.SemanticContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
public class OrganisationService {
    @Autowired
    OrganisationRepo organisationRepo;
    @Autowired
    VolunteerRepo volunteerRepo;
    @Autowired
    EventRepo eventRepo;
    @Autowired
    UserRepo userRepo;
    public OrganisationModel save(Organisation organisation){
        OrganisationModel organisationModel = new OrganisationModel(organisation);
        if(!(organisationRepo.existsById(organisation.getOid()))){
        organisationRepo.save(organisation);
        return organisationModel;}
        return null;
    }
    public EventModel addEvent(int oid,int eid) {
    if(organisationRepo.existsById(oid)&&eventRepo.existsById(eid)){
        Organisation organisation = organisationRepo.findById(oid).get();
        Event event = eventRepo.findById(eid).get();
        if(!(organisation.getEvents().contains(event))){
            event.getOrganisations().add(organisation);
            organisation.getEvents().add(event);
            organisationRepo.save(organisation);
            return new EventModel(event);
        }
        return null;
    }
    return  null;
}
public String assignEvent(int vid,int eid,int oid){
        if(volunteerRepo.existsById(vid)&&eventRepo.existsById(eid)&&organisationRepo.existsById(oid)){
            Event event = eventRepo.findById(eid).get();
            Organisation organisation = organisationRepo.findById(oid).get();
            Volunteer volunteer = volunteerRepo.findById(vid).get();
            if(volunteer.getOrganisations().contains(organisation)&&organisation.getEvents().contains(event)){
                event.getVolunteerList().add(volunteer);
                volunteer.getEvents().add(event);
                volunteerRepo.save(volunteer);
                return "Volunteer Assigned To The Event";
            }
            return "check whether volunteer has registered in this organisation and also check for the presence of this event in that organisation ";
        }
        return  null;
}
public List<Event> viewEventsInOrganisation(int oid){
       if(organisationRepo.existsById(oid)){
           Organisation organisation = organisationRepo.findById(oid).orElse(null);
           if(organisation.getEvents().size()==0)
               return null;
           return organisation.getEvents();
       }
return  null;
}
public String sentMessage(int vid, int oid, String message){
        if(volunteerRepo.existsById(vid)&&organisationRepo.existsById(oid)){
            Volunteer volunteer = volunteerRepo.findById(vid).orElse(null);
            Organisation organisation = organisationRepo.findById(oid).orElse(null);
            if(volunteer.getOrganisations().contains(organisation)){
                LocalDateTime currentDateTime = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm yyyy-MM-dd");
                String formattedDateTime = currentDateTime.format(formatter);
                if(volunteer.getMessages()==null)
                    volunteer.setMessages(new LinkedHashSet<>());
              // LinkedHashSet<String> messages = new LinkedHashSet<>();
                volunteer.getMessages().add(message+" "+formattedDateTime);
                         volunteerRepo.save(volunteer);
            return "Message Sent";}
           return "This Volunteer Is Not Part Of The Organisation";
        }
        return null;
}
    public String groupMessage(int oid, String message) {
        if (organisationRepo.existsById(oid)) {
            Organisation organisation = organisationRepo.findById(oid).orElse(null);
            if (organisation != null) {
                Set<Volunteer> volunteers = new HashSet<>(organisation.getVolunteers()); // Create a copy of the set
                LocalDateTime currentDateTime = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm yyyy-MM-dd");
                String formattedDateTime = currentDateTime.format(formatter);
                String msg = message + " " + formattedDateTime;
                for (Volunteer volunteer : volunteers) {
                    volunteer.getMessages().add(msg);
                    volunteerRepo.save(volunteer);
                }
                return "Sent";
            }
        }
        return "No Organisation Found";
    }
    public String suggestVolunteers(int eid,int oid){
        if(eventRepo.existsById(eid)&&organisationRepo.existsById(oid)){
           Event event = eventRepo.findById(eid).orElse(null);
           Organisation organisation = organisationRepo.findById(oid).orElse(null);
           if(organisation.getEvents().contains(event)){
               Set<String> skills = event.getSkills_good_to_have();
               List<Volunteer> volunteers = new ArrayList<>(organisation.getVolunteers());
               String message = "You Are Very Much Suited For This Event"+" "+event.getName();
               for (Volunteer volunteer : volunteers) {
                   if(CollectionUtils.containsAny(event.getSkills_good_to_have(),volunteer.getSkills())){
                   volunteer.getMessages().add(message);}
                   volunteerRepo.save(volunteer);
               }
               return "Sent";
           }
           return "No Such Event Found In This Organisation";
        }
        return "No Data Found";
    }
}