package com.example.Vms.service.serviceimplementationss;

import com.example.Vms.conversions.EntityToModel;
import com.example.Vms.entities.Event;
import com.example.Vms.entities.Organisation;
import com.example.Vms.entities.User;
import com.example.Vms.entities.Volunteer;
import com.example.Vms.models.EventModel;
import com.example.Vms.models.OrganisationModel;
import com.example.Vms.models.VolunteerModel;
import com.example.Vms.repositories.UserRepo;
import com.example.Vms.repositories.EventRepo;
import com.example.Vms.repositories.OrganisationRepo;
import com.example.Vms.repositories.VolunteerRepo;
import com.example.Vms.service.serviceinterfaces.VolunteerServiceInterface;
import jakarta.transaction.Transactional;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
public class VolunteerService implements VolunteerServiceInterface {
    @Autowired
    VolunteerRepo volunteerRepo;
    @Autowired
    OrganisationRepo organisationRepo;
    @Autowired
    EventRepo eventRepo;
    @Autowired
    UserRepo userRepo;
    @Autowired
    EntityToModel entityToModel;
    public String add(int userId,int organisationId){
       if(organisationRepo.existsById(organisationId)&&userRepo.existsById(userId)) {
           Organisation organisation = organisationRepo.findById(organisationId).orElse(null);
           User user = userRepo.findById(userId).orElse(null);
           if(organisation!=null&&user!=null&&!(user.getOrganisations().contains(organisation))) {
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
   public List<OrganisationModel> totalOrganisations()
   {
        return organisationRepo.findAll().stream().map(entityToModel::organisationToOrganisationModel).toList();
   }
   public List<OrganisationModel> findOrganisationByLoc(String location){
       return organisationRepo.findAll().stream().filter(i->i.getAddress().equals(location)).map(entityToModel::organisationToOrganisationModel).toList();
   }
   public List<VolunteerModel> viewVolunteersInEvent(int eventId, int organisationId){
        if(eventRepo.existsById(eventId)&&organisationRepo.existsById(organisationId)){
            Event event = eventRepo.findById(eventId).orElse(null);
            assert event != null;
            if(event.getStatus().equals("closed"))
                return null;
            Organisation organisation = organisationRepo.findById(organisationId).orElse(null);
            return volunteerRepo.findAll().stream().filter(i->i.getEvents().contains(event)&&i.getOrganisations().contains(organisation)).map(entityToModel::volunteerToVolunteerModel).toList();
        }
        return null;
   }
   public Set<String> showMessages(int volunteerId){
        boolean flag = volunteerRepo.existsById(volunteerId);
        Volunteer volunteer = volunteerRepo.findById(volunteerId).orElse(null);
        if(flag&&volunteer!=null){
            return Objects.requireNonNull(volunteerRepo.findById(volunteerId).orElse(null)).getMessages();
        }
        return null;
   }
   public String CompleteEvent(int volunteerId,int eventId,int organisationId){
        Event event = eventRepo.findById(eventId).orElse(null);
        Organisation organisation = organisationRepo.findById(organisationId).orElse(null);
        Volunteer volunteer = volunteerRepo.findById(volunteerId).orElse(null);
        if(event!=null&&organisation!=null){
               List<Event> events = organisation.getEvents();
               if(events.contains(event)){
                   if(volunteer==null)
                       return "Volunteer Doesn't Exist";
                   if(!(volunteer.getEvents().contains(event)))
                       return "event is not assigned to this volunteer";
                      User user = volunteer.getUser();
                      List<String> certificates = user.getCertificates();
                      certificates.add(user.getName()+"  has participated in the event "+event.getName()+" held in our organisation "+organisation.getName());
                      volunteer.getEvents().remove(event);
                      volunteerRepo.save(volunteer);
                      user.setCertificates(certificates);
                      userRepo.save(user);
                      return "You Have Completed The Event";
               }
               return "This Event Doesn't Exist In This Organisation";
        }
        return  "Data Not Found";
   }
   @Transactional
    public String leaveOrganisation(int volunteerId) {
        Volunteer volunteer = volunteerRepo.findById(volunteerId).orElse(null);
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

            return "Successfully left";
        }
        return "Volunteer Doesn't Exist";
    }
 public List<EventModel> viewEventsRegistered(int volunteerId){
        Volunteer volunteer = volunteerRepo.findById(volunteerId).orElse(null);
        if(volunteer!=null){
            return volunteer.getEvents().stream().filter(i->i.getStatus().equals("active")).distinct().map(entityToModel::eventToEventModel).toList();
        }
        return null;
 }
 public String sendMessageToOrganisation(int organisationId,int volunteerId,String message){
        Organisation organisation = organisationRepo.findById(organisationId).orElse(null);
        Volunteer volunteer = volunteerRepo.findById(volunteerId).orElse(null);
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
 public List<EventModel> searchEventsBySkill(int organiationId,String skill){
        Organisation organisation = organisationRepo.findById(organiationId).orElse(null);
     List<EventModel> events = new ArrayList<>();
        if(organisation!=null&&organisation.getEvents()!=null){
             events = organisation.getEvents().stream().filter(i->i.getSkills_good_to_have().contains(skill)).map(entityToModel::eventToEventModel).toList();
            return events;}
        return null;
 }
 public List<OrganisationModel> searchOrgByAddress(String address){
       List<OrganisationModel> organisations=  organisationRepo.findAll().stream().filter(i->i.getAddress().equals(address)).map(entityToModel::organisationToOrganisationModel).toList();
       if(!(CollectionUtils.isEmpty(organisations)))
           return organisations;
       return null;
 }
 public VolunteerModel get(int volunteerId){
        Volunteer volunteer = volunteerRepo.findById(volunteerId).orElse(null);
        if(volunteer!=null)
            return entityToModel.volunteerToVolunteerModel(volunteer);
        return null;
 }
 public List<EventModel> eventsRegisteredByVolInOrg(int volunteerId,int organisationId){
        Volunteer volunteer = volunteerRepo.findById(volunteerId).orElse(null);
     Organisation organisation = organisationRepo.findById(organisationId).orElse(null);
     if(volunteer!=null&&organisation!=null){
         return volunteerRepo.findEventsByVolunteerAndOrganisation(volunteerId,organisationId).stream().map(entityToModel::eventToEventModel).toList();
     }
     return null;
 }
}