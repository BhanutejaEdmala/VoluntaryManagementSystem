package com.example.Vms.service;

import com.example.Vms.entities.Event;
import com.example.Vms.entities.Organisation;
import com.example.Vms.entities.User;
import com.example.Vms.entities.Volunteer;
import com.example.Vms.repositories.UserRepo;
import com.example.Vms.repositories.EventRepo;
import com.example.Vms.repositories.OrganisationRepo;
import com.example.Vms.repositories.VolunteerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class VolunteerService {
    @Autowired
    VolunteerRepo volunteerRepo;
    @Autowired
    OrganisationRepo organisationRepo;
    @Autowired
    EventRepo eventRepo;
    @Autowired
    UserRepo userRepo;
    public String add(int uid,int oid){
       if(organisationRepo.existsById(oid)&&userRepo.existsById(uid)) {
           Organisation organisation = organisationRepo.findById(oid).get();
           User user = userRepo.findById(uid).get();
           if(!(user.getOrganisations().contains(organisation))) {
               user.getOrganisations().add(organisation);
                   Volunteer volunteer = new Volunteer(user);
                   volunteer.setUser(user);
                   user.getVolunteers().add(volunteer);
                Set<Organisation> organisationList =   volunteer.getOrganisations();
                organisationList.add(organisation);
                volunteer.setOrganisations(organisationList);
                   organisation.getVolunteers().add(volunteer);
               userRepo.save(user);
                   organisationRepo.save(organisation);
               return "Registered Succesfully";
           }
           else
               return "you already registered as a volunteer in this organisaton";
       }
       return "Invalid Data";
    }
   public List<Organisation> totalOrganisations(){
        return organisationRepo.findAll();
   }
   public List<Organisation> findOrganisationByLoc(String location){
       return organisationRepo.findAll().stream().filter(i->i.getAddress().equals(location)).toList();
   }
   public List<Volunteer> viewVolunteersInEvent(int eid,int oid){
        if(eventRepo.existsById(eid)&&organisationRepo.existsById(oid)){
            Event event = eventRepo.findById(eid).orElse(null);
            Organisation organisation = organisationRepo.findById(oid).orElse(null);
            return volunteerRepo.findAll().stream().filter(i->i.getEvents().contains(event)&&i.getOrganisations().contains(organisation)).toList();
        }
        return null;
   }
   public Set<String> showMessages(int vid){
        if(volunteerRepo.existsById(vid)){
            return volunteerRepo.findById(vid).orElse(null).getMessages();
        }
        return null;
   }
}
