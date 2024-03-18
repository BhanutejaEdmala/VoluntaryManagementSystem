package com.example.Vms.conversions;

import com.example.Vms.entities.Event;
import com.example.Vms.entities.Organisation;
import com.example.Vms.entities.User;
import com.example.Vms.entities.Volunteer;
import com.example.Vms.models.EventModel;
import com.example.Vms.models.OrganisationModel;
import com.example.Vms.models.UserModel;
import com.example.Vms.models.VolunteerModel;
import org.springframework.stereotype.Component;

@Component
public class ModelToEntity {
    public User userModelToEntity(UserModel userModel){
        User user = new User();
        user.setName(userModel.getName());
        user.setPassword(userModel.getPassword());
        user.setRoles(userModel.getRoles());
        user.setAddress(userModel.getAddress());
         user.setSkills(userModel.getSkills());
         user.setOrganisations(userModel.getOrganisations());
         user.setVolunteers(userModel.getVolunteers());
         user.setCertificates(userModel.getCertificates());
         return user;
    }
    public Event EventModelToEvent(EventModel eventModel){
        Event event = new Event();
        event.setName(eventModel.getName());
        event.setLocation(eventModel.getLocation());
        event.setDate(eventModel.getDate());
        event.setOrganisations(eventModel.getOrganisations());
        event.setVolunteerList(eventModel.getVolunteerList());
        event.setStatus(event.getStatus());
        event.setTimings(eventModel.getTimings());
        event.setSkills_good_to_have(eventModel.getSkills_good_to_have());
        return event;
    }
    public Organisation organisationModelToOrganisation(OrganisationModel organisationModel){
        Organisation organisation = new Organisation();
        organisation.setName(organisationModel.getName());
        organisation.setAddress(organisationModel.getAddress());
        organisation.setEvents(organisationModel.getEvents());
        organisation.setMobile(organisationModel.getMobile());
        organisation.setVolunteers(organisationModel.getVolunteers());
        return organisation;
    }
    public Volunteer volunteerModelToVolunteer(VolunteerModel volunteerModel){
        Volunteer volunteer = new Volunteer();
        volunteer.setName(volunteerModel.getName());
        volunteer.setAddress(volunteerModel.getAddress());
        volunteer.setEvents(volunteerModel.getEvents());
        volunteer.setSkills(volunteerModel.getSkills());
        volunteer.setMessages(volunteerModel.getMessages());
        volunteer.setOrganisations(volunteerModel.getOrganisations());
        return volunteer;
    }
}
