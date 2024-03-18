package com.example.Vms.service.serviceimplementationss;

import com.example.Vms.conversions.EntityToModel;
import com.example.Vms.conversions.ModelToEntity;
import com.example.Vms.entities.Event;
import com.example.Vms.entities.Organisation;
import com.example.Vms.entities.User;
import com.example.Vms.entities.Volunteer;
import com.example.Vms.models.EventModel;
import com.example.Vms.models.OrganisationModel;
import com.example.Vms.repositories.EventRepo;
import com.example.Vms.repositories.OrganisationRepo;
import com.example.Vms.repositories.UserRepo;
import com.example.Vms.repositories.VolunteerRepo;
import com.example.Vms.service.serviceinterfaces.OrganisationServiceInterface;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.util.CollectionUtils;

import javax.xml.crypto.Data;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@org.springframework.stereotype.Service
public class OrganisationService implements OrganisationServiceInterface {
    @Autowired
    OrganisationRepo organisationRepo;
    @Autowired
    VolunteerRepo volunteerRepo;
    @Autowired
    EventRepo eventRepo;
    @Autowired
    UserRepo userRepo;
    @Autowired
    UserService userService;
    @Autowired
    VolunteerService volunteerService;
    @Autowired
    EntityToModel entityToModel;
    @Autowired
    ModelToEntity modelToEntity;
    public OrganisationModel save(OrganisationModel organisationModel){
       Organisation organisation = modelToEntity.organisationModelToOrganisation(organisationModel);
        if(!(organisationRepo.existsById(organisation.getOid()))){
        organisationRepo.save(organisation);
        return organisationModel;}
        return null;
    }
    public String addEvent(int oid,int eid) {
    if(organisationRepo.existsById(oid)&&eventRepo.existsById(eid)){
        Organisation organisation = organisationRepo.findById(oid).orElse(null);
        Event event = eventRepo.findById(eid).orElse(null);
        if(organisation!=null&&event!=null&&!(organisation.getEvents().contains(event))&&event.getStatus().equals("active")){
            event.getOrganisations().add(organisation);
            organisation.getEvents().add(event);
            organisationRepo.save(organisation);
            return "Event Added";
        }
        return "This Event Might Be Already Present In This Organisation OR This Event Might Be Closed";
    }
    return  "Check Whether Those Organisation And Event Exist In The First Place";
}
public String assignEvent(int vid,int eid,int oid){
        if(volunteerRepo.existsById(vid)&&eventRepo.existsById(eid)&&organisationRepo.existsById(oid)){
            Event event = eventRepo.findById(eid).orElse(null);
            Organisation organisation = organisationRepo.findById(oid).orElse(null);
            Volunteer volunteer = volunteerRepo.findById(vid).orElse(null);
            List<Event> events = new ArrayList<>();
             if(volunteer!=null)
                events = volunteer.getEvents();
             if(events.stream().anyMatch(event1 -> {
                if(event!=null)
                    if(event1.getDate().equals(event.getDate())){
                String[] existTimings = event1.getTimings().split("to");
                String[] newTimings= event.getTimings().split("to");
                return timingsCompare(newTimings,existTimings);}
                return false;
            })){
                return "Volunteer Already Assigned To Another Event During The Timings Of This Event";}
            if(volunteer!=null&&volunteer.getOrganisations().contains(organisation)&&organisation!=null&&organisation.getEvents().contains(event)&&event!=null){
                if(!(event.getStatus().equals("active"))||organisation.getClosedevents().contains(eid))
                    return "This Event Is Closed";
                event.getVolunteerList().add(volunteer);
                volunteer.getEvents().add(event);
                volunteerRepo.save(volunteer);
                return "Volunteer Assigned To The Event";
            }
            return "check whether volunteer has registered in this organisation and also check for the presence of this event in that organisation ";
        }
        return  null;
}
public List<EventModel> viewEventsInOrganisation(int oid){
       if(organisationRepo.existsById(oid)){
           Organisation organisation = organisationRepo.findById(oid).orElse(null);
           if(organisation!=null&&organisation.getEvents().isEmpty())
               return new ArrayList<>();
           return organisation!=null?organisation.getEvents().stream().filter(i->!(i.getStatus().equals("closed"))).map(entityToModel::eventToEventModel).toList():null;
       }
return  null;
}
public String sendMessage(int vid, int oid, String message){
        if(volunteerRepo.existsById(vid)&&organisationRepo.existsById(oid)){
            Volunteer volunteer = volunteerRepo.findById(vid).orElse(null);
            Organisation organisation = organisationRepo.findById(oid).orElse(null);
            if(volunteer!=null&&volunteer.getOrganisations().contains(organisation)){
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
           if(event!=null&&organisation!=null&&organisation.getEvents().contains(event)){
               Set<String> skills = event.getSkills_good_to_have();
               List<Volunteer> volunteers = new ArrayList<>(organisation.getVolunteers());
               LocalDateTime currentDateTime = LocalDateTime.now();
               DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm yyyy-MM-dd");
               String formattedDateTime = currentDateTime.format(formatter);
               String message = "You Are Very Much Suited For This Event"+" '"+event.getName()+"', time received:"+formattedDateTime;
               for (Volunteer volunteer : volunteers) {
                   if(CollectionUtils.containsAny(skills,volunteer.getSkills())){
                   volunteer.getMessages().add(message);}
                   volunteerRepo.save(volunteer);
               }
               return "Sent To All Matching Volunteers";
           }
           return "No Such Event Found In This Organisation";
        }
        return "No Data Found";
    }
    @Transactional
    public String removeOrganization(int oid) {
        Organisation organisation = organisationRepo.findById(oid).orElse(null);
        if (organisation != null) {
            // Retrieve related entities
            List<Volunteer> volunteers = new ArrayList<>(organisation.getVolunteers());
            List<Event> events = organisation.getEvents();
            // Remove the organization from related volunteers
            volunteers.stream().filter(i -> i.getOrganisations().contains(organisation))
                    .forEach(volunteer -> {
                        volunteer.getOrganisations().remove(organisation);
                        volunteerRepo.delete(volunteer);
                    });
            // Remove the organization from related events
            events.stream().filter(i->i.getOrganisations().contains(organisation)).forEach(event -> event.getOrganisations().remove(organisation));
            // Clear organization references from related entities
            organisation.getVolunteers().clear();
            organisation.getEvents().clear();
            // Remove the organization from users
            List<User> users = organisation.getUsers();
            users.forEach(user -> user.getOrganisations().remove(organisation));
            organisation.getUsers().clear();
            // Save changes
            organisationRepo.save(organisation);
            organisationRepo.delete(organisation);
            return "Organisation with ID " + oid + " has been removed.";
        }
        return "Organisation with ID " + oid + " does not exist.";
    }
    public String updateOrganisation(Organisation organisation,int oid){
        Organisation organisation1 = organisationRepo.findById(oid).orElse(null);
        if(organisation1!=null){
            organisation1.setName(organisation.getName());
            organisation1.setAddress(organisation.getAddress());
            organisation1.setMobile(organisation.getMobile());
           organisationRepo.save(organisation1);
           return "Updated";
        }
        return "organisation doesn't exist";
    }
    public String closeEventForOrg(int eid,int oid){
        Event event = eventRepo.findById(eid).orElse(null);
        Organisation organisation = organisationRepo.findById(oid).orElse(null);
        List<Integer> events = new ArrayList<>();;
        if(event!=null&&organisation!=null) {
            List<Volunteer> volunteers = event.getVolunteerList();
            if(!(organisation.getEvents().contains(event)))
                return "No Such Event Found In This Organisation";
            if (organisation.getClosedevents() != null) {
                events.add(eid);
                organisation.setClosedevents(events);
            } else {
                events.add(eid);
                organisation.setClosedevents(events);
            }
            if(!(volunteers.isEmpty())){
                List<Volunteer> volunteersToRemove = new ArrayList<>(volunteers);
                for (Volunteer volunteer : volunteersToRemove) {
                    int uid = volunteer.getUser().getUid();
                    try{
                    userService.leaveEvent(uid, eid, oid);}
                    catch(Exception e){
                    System.out.println();}
                }
                organisationRepo.save(organisation);
            }
            return "Event Closed";
        }
        return "No Data Found";
    }
    @Transactional
    public String removeVolunteer(int vid) {
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
            return "Volunteer is Deleted";
        }
        return "Volunteer Doesn't Exist";
    }
    public List<String> viewMessagesOfVolunteers(int oid){
        Organisation organisation = organisationRepo.findById(oid).orElse(null);
        return organisation!=null?organisation.getMessages():null;
    }
    public OrganisationModel get(int oid){
        Organisation organisation = organisationRepo.findById(oid).orElse(null);
        return organisation!=null ? entityToModel.organisationToOrganisationModel(organisation):null;
    }
    public String closeEvent(int eid){
        Event event = eventRepo.findById(eid).orElse(null);
        if(event!=null){
            List<Volunteer> volunteers = new ArrayList<>();
            volunteers = event.getVolunteerList();
            volunteers.forEach(volunteer ->  volunteer.getEvents().remove(event));
            volunteerRepo.saveAll(volunteers);
            event.getVolunteerList().clear();
            event.setStatus("closed");
            eventRepo.save(event);
            return "event closed";}
        return "event doesn't exist";
    }
    public boolean timingsCompare(String[] newTimings,String[] existTimings){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
        LocalTime existStartTime = LocalTime.parse(existTimings[0].strip(),formatter);
        LocalTime existEndTime = LocalTime.parse(existTimings[1].strip(),formatter);
        LocalTime newStartTime = LocalTime.parse(newTimings[0].strip(),formatter);
        LocalTime newEndTime = LocalTime.parse(newTimings[1].strip(),formatter);
        if(!(newStartTime.isAfter(existStartTime) && !(newStartTime.isAfter(existEndTime))))
            return true;
        else if(newStartTime.equals(existStartTime))
            return true;
        else if(newStartTime.isBefore(existEndTime))
            return true;
        return false;
    }
}