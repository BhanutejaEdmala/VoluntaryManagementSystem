package com.example.Vms.service.serviceimplementations;

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
import org.springframework.util.CollectionUtils;
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
    EntityToModel entityToModel;
    @Autowired
    ModelToEntity modelToEntity;
    @Autowired
    VolunteerService volunteerService;

    public String saveOrganisation(OrganisationModel organisationModel, String userName, String password) {
        Organisation organisation = modelToEntity.organisationModelToOrganisation(organisationModel);
        if (!(organisationRepo.existsById(organisation.getOid()))) {
            if (!(userRepo.existsByName(userName)))
                return "No Account Details Found , Create An Account To Save An Organisation";
            organisation.getAdminDetails().addAll(List.of(userName, Base64.getEncoder().encodeToString(password.getBytes())));
            organisationRepo.save(organisation);
            return "Organisation Saved";
        }
        return null;
    }

    public String addEvent(String username, String password, int organisationId, int eventId) {
        if (organisationRepo.existsById(organisationId) && eventRepo.existsById(eventId)) {
            Organisation organisation = organisationRepo.findById(organisationId).orElse(null);
            Event event = eventRepo.findById(eventId).orElse(null);
            if (null != organisation && null != event && !(organisation.getEvents().contains(event)) && event.getStatus().equals("active")) {
                if (!(userRepo.existsByName(username)))
                    return "User Not Found";
                if (authorisationCheck(username, password, organisationId))
                    return "Authorisation Failed";
                event.getOrganisations().add(organisation);
                organisation.getEvents().add(event);
                organisationRepo.save(organisation);
                return "Event Added";
            }
            return "This Event Might Already Be Present In The Organisation OR This Event Might Be Closed";
        }
        return "Check Whether Those Organisation And Event Exist In The First Place";
    }

    public String assignEvent(String userName,String password,int volunteerId, int eventId, int organisationId) {
        if(authorisationCheck(userName,password,organisationId))
            return "You Are Not Authorised To Do This";
        if (volunteerRepo.existsById(volunteerId) && eventRepo.existsById(eventId) && organisationRepo.existsById(organisationId)) {
            Event event = eventRepo.findById(eventId).orElse(null);
            Organisation organisation = organisationRepo.findById(organisationId).orElse(null);
            Volunteer volunteer = volunteerRepo.findById(volunteerId).orElse(null);
            List<Event> events = new ArrayList<>();
            if (null != volunteer)
                events = volunteer.getEvents();
            if (events.stream().anyMatch(event1 -> {
                if (null != event)
                    if (event1.getDate().equals(event.getDate())) {
                        String[] existTimings = event1.getTimings().split("to");
                        String[] newTimings = event.getTimings().split("to");
                        return timingsCompare(newTimings, existTimings);
                    }
                return false;
            })) {
                return "Volunteer Already Assigned To Another Event During The Timings Of This Event";
            }
            if (null != volunteer && volunteer.getOrganisations().contains(organisation) && null != organisation && organisation.getEvents().contains(event) && null != event) {
                if (!(event.getStatus().equals("active")) || organisation.getClosedevents().contains(eventId))
                    return "This Event Is Closed";
                event.getVolunteerList().add(volunteer);
                volunteer.getEvents().add(event);
                volunteerRepo.save(volunteer);
                return "Volunteer Assigned To The Event";
            }
            return "check whether volunteer has registered in this organisation and also check for the presence of this event in that organisation ";
        }
        return null;
    }

    public List<EventModel> viewEventsInOrganisation(int organisationId) {
        if (organisationRepo.existsById(organisationId)) {
            Organisation organisation = organisationRepo.findById(organisationId).orElse(null);
            if (null != organisation && CollectionUtils.isEmpty(organisation.getEvents()))
                return new ArrayList<>();
            return organisation != null ? organisation.getEvents().stream().filter(i -> !(i.getStatus().equals("closed"))).map(entityToModel::eventToEventModel).toList() : null;
        }
        return null;
    }

    public String sendMessage(String userName,String password,int volunteerId, int organisationId, String message) {
        if(authorisationCheck(userName,password,organisationId))
            return "You're Not Authorised To Do This";
        if (volunteerRepo.existsById(volunteerId) && organisationRepo.existsById(organisationId)) {
            Volunteer volunteer = volunteerRepo.findById(volunteerId).orElse(null);
            Organisation organisation = organisationRepo.findById(organisationId).orElse(null);
            if (null != volunteer && volunteer.getOrganisations().contains(organisation)) {
                LocalDateTime currentDateTime = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm yyyy-MM-dd");
                String formattedDateTime = currentDateTime.format(formatter);
                if (null == volunteer.getMessages())
                    volunteer.setMessages(new LinkedHashSet<>());
                volunteer.getMessages().add(message + " " + formattedDateTime);
                volunteerRepo.save(volunteer);
                return "Message Sent";
            }
            return "This Volunteer Is Not Part Of The Organisation";
        }
        return null;
    }

    public String groupMessage(String userName,String password,int organisationId, String message) {
        if(authorisationCheck(userName,password,organisationId))
            return "You're Not Authorised To Do This";
        if (organisationRepo.existsById(organisationId)) {
            Organisation organisation = organisationRepo.findById(organisationId).orElse(null);
            if (null != organisation) {
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

    public String suggestVolunteers(String userName,String password,int eventId, int organisationId) {
        if(authorisationCheck(userName,password,organisationId))
            return "You're Not Authorised To Do This";
        if (eventRepo.existsById(eventId) && organisationRepo.existsById(organisationId)) {
            Event event = eventRepo.findById(eventId).orElse(null);
            Organisation organisation = organisationRepo.findById(organisationId).orElse(null);
            if (null != event && null != organisation && organisation.getEvents().contains(event)) {
                Set<String> skills = event.getSkills_good_to_have();
                List<Volunteer> volunteers = new ArrayList<>(organisation.getVolunteers());
                LocalDateTime currentDateTime = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm yyyy-MM-dd");
                String formattedDateTime = currentDateTime.format(formatter);
                String message = "You Are Very Much Suited For This Event" + " '" + event.getName() + "', time received:" + formattedDateTime;
                if (volunteers.stream().anyMatch(i -> CollectionUtils.containsAny(skills, i.getSkills()))) {
                    for (Volunteer volunteer : volunteers) {
                        if (CollectionUtils.containsAny(skills, volunteer.getSkills())) {
                            volunteer.getMessages().add(message);
                        }
                        volunteerRepo.save(volunteer);
                    }
                    return "Sent To All Matching Volunteers";
                } else {
                    return "There Are No Suitable Volunteers";
                }
            }
            return "No Such Event Found In This Organisation";
        }
        return "Check The Details You've Entered";
    }

    @Transactional
    public String removeOrganization(String userName,String password,int organisationId) {
        if(authorisationCheck(userName,password,organisationId))
            return "You're Not Authorised To Do This";
        Organisation organisation = organisationRepo.findById(organisationId).orElse(null);
        if (null != organisation) {
            // Retrieve related entities
            List<Volunteer> volunteers = new ArrayList<>(organisation.getVolunteers());
            List<Event> events = organisation.getEvents();
            // Remove the organization from related volunteers
            volunteers.forEach(volunteer -> removeVolunteer(userName,password, organisationId,volunteer.getVid()));
            // Remove the organization from related events
            events.stream().filter(i -> i.getOrganisations().contains(organisation)).forEach(event -> event.getOrganisations().remove(organisation));
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
            return "Organisation with ID " + organisationId + " has been removed.";
        }
        return "Organisation with ID " + organisationId + " does not exist.";
    }
@org.springframework.transaction.annotation.Transactional
    public String updateOrganisation(String userName,String password,Organisation organisation, int organisationId) {
        if(authorisationCheck(userName,password,organisationId))
            return "You're Not Authorised To Do This";
        Organisation organisation1 = organisationRepo.findById(organisationId).orElse(null);
        if (null != organisation1) {
            organisation1.setName(organisation.getName());
            organisation1.setAddress(organisation.getAddress());
            organisation1.setMobile(organisation.getMobile());
            organisationRepo.save(organisation1);
            return "Organisation Updated";
        }
        return "organisation doesn't exist";
    }

    public String closeEventForOrg(String userName,String password,int eventId, int organisationID) {
        if(authorisationCheck(userName,password,organisationID))
            return "You're Not Authorised To Do This";
        Event event = eventRepo.findById(eventId).orElse(null);
        Organisation organisation = organisationRepo.findById(organisationID).orElse(null);
        List<Integer> events = new ArrayList<>();
        ;
        if (null != event && null != organisation) {
            List<Volunteer> volunteers = event.getVolunteerList();
            if (!(organisation.getEvents().contains(event)))
                return "No Such Event Found In This Organisation";
            if (organisation.getClosedevents() != null) {
                events.add(eventId);
                organisation.setClosedevents(events);
            } else {
                events.add(eventId);
                organisation.setClosedevents(events);
            }
            if (!(CollectionUtils.isEmpty(volunteers))) {
                List<Volunteer> volunteersToRemove = new ArrayList<>(volunteers);
                for (Volunteer volunteer : volunteersToRemove) {
                    int uid = volunteer.getUser().getUid();
                    try {
                        userService.leaveEvent(uid, eventId, organisationID);
                    } catch (Exception e) {
                        System.out.println();
                    }
                }
                organisationRepo.save(organisation);
            }
            return "Event Closed";
        }
        return "Check The Details You've Entered";
    }

    @Transactional
    public String removeVolunteer(String username ,String password,int organisationId,int volunteerId) {
        Volunteer volunteer = volunteerRepo.findById(volunteerId).orElse(null);
        if (null != volunteer) {
            List<Organisation> organisations = organisationRepo.findAll();
            List<Event> events = eventRepo.findAll();
            User user = volunteer.getUser();
            // Remove volunteer from events
             if(!(CollectionUtils.isEmpty(events)))
            events.stream().filter(i -> i.getVolunteerList().contains(volunteer)).forEach(event -> event.getVolunteerList().remove(volunteer));
            // Remove volunteer from organisations
             if(!(CollectionUtils.isEmpty(organisations)))
                organisations.stream().filter(i -> i.getVolunteers().contains(volunteer)).forEach(organisation -> organisation.getVolunteers().remove(volunteer));
            // Remove volunteer from user
            if(null!=user)
             user.getVolunteers().remove(volunteer);
            // Save changes
            eventRepo.saveAll(events);
            organisationRepo.saveAll(organisations);
            if(null!=user){
                user.getMessages().add("You Are Removed As Volunteer From Organisation");
            userRepo.save(user);}
            // Delete the volunteer from the repository
            volunteerRepo.delete(volunteer);
            return "Volunteer is Deleted";
        }
        return "Volunteer Doesn't Exist";
    }

    public List<String> viewMessagesFromVolunteers(String userName,String password,int organisationId) {
        if(authorisationCheck(userName,password,organisationId))
            return new ArrayList<>();
        Organisation organisation = organisationRepo.findById(organisationId).orElse(null);
        return organisation != null ? organisation.getMessages() : null;
    }

    public OrganisationModel getOrganisation(int organisationId) {
        Organisation organisation = organisationRepo.findById(organisationId).orElse(null);
        return null != organisation ? entityToModel.organisationToOrganisationModel(organisation) : null;
    }

    public String closeEvent(int eventId) {
        Event event = eventRepo.findById(eventId).orElse(null);
        if (null != event) {
            List<Volunteer> volunteers = new ArrayList<>();
            volunteers = event.getVolunteerList();
            volunteers.forEach(volunteer -> volunteer.getEvents().remove(event));
            volunteerRepo.saveAll(volunteers);
            event.getVolunteerList().clear();
            event.setStatus("closed");
            eventRepo.save(event);
            return "event closed";
        }
        return "event doesn't exist";
    }

    public boolean timingsCompare(String[] newTimings, String[] existTimings) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
        LocalTime existStartTime = LocalTime.parse(existTimings[0].strip(), formatter);
        LocalTime existEndTime = LocalTime.parse(existTimings[1].strip(), formatter);
        LocalTime newStartTime = LocalTime.parse(newTimings[0].strip(), formatter);
        LocalTime newEndTime = LocalTime.parse(newTimings[1].strip(), formatter);
        if (newEndTime.equals(existStartTime))
            return false;
        else if (newStartTime.isAfter(existEndTime))
            return false;
        else if (newEndTime.isBefore(existStartTime))
            return false;
        else if (newStartTime.isAfter(existStartTime) && newEndTime.isBefore(existEndTime))
            return true;
        else if (newStartTime.isBefore(existStartTime) && newEndTime.isBefore(existEndTime))
            return true;
        else return newStartTime.equals(existStartTime);
    }

    public String addEventInOrganisation(String username, String password, EventModel eventModel, int oid) {
        if (authorisationCheck(username, password, oid))
            return "Authorization Failed";
        Event event = modelToEntity.EventModelToEvent(eventModel);
        Event event1 = eventRepo.save(event);
        return addEvent(username, password, oid, event1.getEid());
    }
    boolean authorisationCheck(String username, String password, int oid) {
        Organisation organisation = organisationRepo.findById(oid).orElse(null);
        assert organisation != null;
        List<String> details = new ArrayList<>(organisation.getAdminDetails());
        if (CollectionUtils.isEmpty(organisation.getAdminDetails()))
            details = new ArrayList<>(organisation.getAdminDetails());
        String encodedString = details.get(1);
        byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
        String decodedString = new String(decodedBytes);
        return !(details.get(0).equals(username) && decodedString.equals(password));
    }
    public String approveJoinRequests(String userName,String password,int organisationId) {
        if(!(userRepo.existsByName(userName)))
            return "User Not Found";
        if(authorisationCheck(userName,password,organisationId))
            return "You Are Not Authorised To Do This";
        Organisation organisation = organisationRepo.findById(organisationId).orElse(null);
        List<Integer> waitingIds;
        if (null != organisation)
            waitingIds = organisation.getWaitingListUserIds();
        else
            return "No Organisation Found";
        if (CollectionUtils.isEmpty(waitingIds))
            return "Waiting List Is Empty";
        List<User> users = waitingIds.stream().map(id -> userRepo.findById(id).orElse(null)).filter(Objects::nonNull).toList();
        organisation.getWaitingListUserIds().clear();
        organisationRepo.save(organisation);
        users.forEach(user -> volunteerService.add(user.getName(), organisationId));
        return "Requests Approved Successfully";
    }
    public String approveSingleRequest(String username,String password,String user,int organisationId){
        if(!(userRepo.existsByName(username)))
            return "User Not Found";
        if(authorisationCheck(username,password,organisationId))
            return "You Are Not Authorised To Do This";
        Organisation organisation = organisationRepo.findById(organisationId).orElse(null);
        List<Integer> waitingIds = new ArrayList<>();
        if (null != organisation)
            waitingIds = organisation.getWaitingListUserIds();
        else
            return "No Organisation Found";
        if (CollectionUtils.isEmpty(waitingIds))
            return "Waiting List Is Empty";
        if(!(userRepo.existsByName(user)))
            return user+" not found";
        User user1 = userRepo.findByName(user).orElse(null);
        if(null==user1||!(waitingIds.contains(user1.getUid())))
            return user+" is Not In The Waiting List";
        volunteerService.add(user1.getName(),organisationId);
        organisation.getWaitingListUserIds().remove(user1.getUid());
        organisationRepo.save(organisation);
        return "Request Accepted";
    }
    public String rejectingRequests(String username,String password,int organisationId){
        if(userRepo.findAll().stream().noneMatch(user->user.getName().equals(username)))
            return "User Not Found";
        if(authorisationCheck(username,password,organisationId))
            return "You Are Not Authorised To Do This";
        Organisation organisation = organisationRepo.findById(organisationId).orElse(null);
        List<Integer> waitingIds;
        if (null != organisation)
            waitingIds = organisation.getWaitingListUserIds();
        else {
            return "No Organisation Found";
        }
        if (CollectionUtils.isEmpty(waitingIds))
            return "Waiting List Is Empty";
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm yyyy-MM-dd");
        String formattedDateTime = currentDateTime.format(formatter);
        userRepo.findAll().stream().filter(user -> waitingIds.contains(user.getUid())).forEach(user->{user.getMessages().add("Your Request To Join In"+organisation.getName()+" has been rejected "+formattedDateTime);
        userRepo.save(user);});
        organisation.getWaitingListUserIds().clear();
        organisationRepo.save(organisation);
           return "Rejected";
    }
}
