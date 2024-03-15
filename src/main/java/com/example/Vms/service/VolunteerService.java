package com.example.Vms.service;

import com.example.Vms.entities.Event;
import com.example.Vms.entities.Organisation;
import com.example.Vms.entities.User;
import com.example.Vms.entities.Volunteer;
import com.example.Vms.repositories.UserRepo;
import com.example.Vms.repositories.EventRepo;
import com.example.Vms.repositories.OrganisationRepo;
import com.example.Vms.repositories.VolunteerRepo;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
            if(event.getStatus().equals("closed"))
                return null;
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
   public String CompleteEvent(int vid,int eid,int oid){
        Event event = eventRepo.findById(eid).orElse(null);
        Organisation organisation = organisationRepo.findById(oid).orElse(null);
        Volunteer volunteer = volunteerRepo.findById(vid).orElse(null);
        if(event!=null&&organisation!=null){
               List<Event> events = organisation.getEvents();
               if(events.contains(event)){
                   if(!(volunteer.getEvents().contains(event)))
                       return "event is not assigned to this volunteer";
                   if(volunteer!=null){
                      User user = volunteer.getUser();
                      List<String> certificates = user.getCertificates();
                      certificates.add(user.getName()+"  has participated in the event "+event.getName()+" held in our organisation "+organisation.getName());
                      volunteer.getEvents().remove(event);
                      volunteerRepo.save(volunteer);
                      user.setCertificates(certificates);
                      userRepo.save(user);
                      return "Done";
                    }
                   return "Volunteer Doesn't Exist";
               }
               return "This Event Doesn't Exist In This Organisation";
        }
        return  "Data Not Found";
   }
   @Transactional
    public String leaveOrganisation(int vid) {
        Volunteer volunteer = volunteerRepo.findById(vid).orElse(null);
        if (volunteer != null) {
            List<Organisation> organisations = organisationRepo.findAll();
            List<Event> events = eventRepo.findAll();
            User user = volunteer.getUser();

            // Remove volunteer from events
            events.stream().filter(i->i.getVolunteerList().contains(volunteer)).forEach(event -> event.getVolunteerList().remove(volunteer));

            // Remove volunteer from organisations
            organisations.stream().filter(i->i.getVolunteers().contains(volunteer)).forEach(organisation -> organisation.getVolunteers().remove(volunteer));

            // Remove volunteer from user
            user.getVolunteers().remove(volunteer);

            // Save changes
            eventRepo.saveAll(events);
            organisationRepo.saveAll(organisations);
            userRepo.save(user);

            // Delete the volunteer from the repository
            volunteerRepo.delete(volunteer);

            return "Deleted";
        }
        return "Volunteer Doesn't Exist";
    }
 public List<Event> viewEventsRegistered(int vid){
        Volunteer volunteer = volunteerRepo.findById(vid).orElse(null);
        if(volunteer!=null){
            return volunteer.getEvents().stream().filter(i->i.getStatus().equals("active")).distinct().toList();
        }
        return null;
 }
 public String sendMessageToOrganisation(int oid,int vid,String message){
        Organisation organisation = organisationRepo.findById(oid).orElse(null);
        Volunteer volunteer = volunteerRepo.findById(vid).orElse(null);
        if(organisation!=null&&volunteer!=null){
            LocalDateTime currentDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm yyyy-MM-dd");
            String formattedDateTime = currentDateTime.format(formatter);
            String msg = "volunteer id: "+volunteer.getVid()+" volunteer name: "+volunteer.getName()+" :message=>"+message+", time received "+formattedDateTime;
            List<String> messages = new ArrayList<>();
            if(organisation.getMessages()==null){
                organisation.setMessages(messages);
            }
            else{
                messages=organisation.getMessages();
            }
            messages.add(msg);
            organisationRepo.save(organisation);
            return "Sent";
        }
        return "Data Invalid";
 }
 public List<Event> searchEventsBySkill(int oid,String skill){
        Organisation organisation = organisationRepo.findById(oid).orElse(null);
     List<Event> events = new ArrayList<>();
        if(organisation.getEvents()!=null){
             events = organisation.getEvents().stream().filter(i->i.getSkills_good_to_have().contains(skill)).toList();
            return events;}
        return null;
 }
 public List<Organisation> searchOrgByAddress(String address){
       List<Organisation> organisations=  organisationRepo.findAll().stream().filter(i->i.getAddress().equals(address)).toList();
       if(!(organisations.isEmpty()))
           return organisations;
       return null;
 }
 public Volunteer get(int vid){
        Volunteer volunteer = volunteerRepo.findById(vid).orElse(null);
        if(volunteer!=null)
            return volunteer;
        return null;
 }
}
