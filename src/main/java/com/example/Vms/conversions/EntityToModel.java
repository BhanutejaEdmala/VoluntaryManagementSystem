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
public class EntityToModel {
    public UserModel userEntityToModel(User userEntity) {
        UserModel userModel = new UserModel();
        userModel.setName(userEntity.getName());
        userModel.setPassword(userEntity.getPassword());
        userModel.setRoles(userEntity.getRoles());
        userModel.setAddress(userEntity.getAddress());
        userModel.setSkills(userEntity.getSkills());
        return userModel;
    }

    public EventModel eventToEventModel(Event eventEntity) {
        EventModel eventModel = new EventModel();
        eventModel.setName(eventEntity.getName());
        eventModel.setLocation(eventEntity.getLocation());
        eventModel.setDate(eventEntity.getDate());
        eventModel.setOrganisations(eventEntity.getOrganisations());
        eventModel.setVolunteerList(eventEntity.getVolunteerList());
        eventModel.setStatus(eventEntity.getStatus());
        eventModel.setTimings(eventEntity.getTimings());
        eventModel.setSkills_good_to_have(eventEntity.getSkills_good_to_have());
        return eventModel;
    }

    public OrganisationModel organisationToOrganisationModel(Organisation organisationEntity) {
        OrganisationModel organisationModel = new OrganisationModel();
        organisationModel.setName(organisationEntity.getName());
        organisationModel.setAddress(organisationEntity.getAddress());
        organisationModel.setEvents(organisationEntity.getEvents());
        organisationModel.setMobile(organisationEntity.getMobile());
        organisationModel.setVolunteers(organisationEntity.getVolunteers());
        return organisationModel;
    }

    public VolunteerModel volunteerToVolunteerModel(Volunteer volunteerEntity) {
        VolunteerModel volunteerModel = new VolunteerModel();
        volunteerModel.setName(volunteerEntity.getName());
        volunteerModel.setAddress(volunteerEntity.getAddress());
        volunteerModel.setEvents(volunteerEntity.getEvents());
        volunteerModel.setSkills(volunteerEntity.getSkills());
        volunteerModel.setMessages(volunteerEntity.getMessages());
        volunteerModel.setOrganisations(volunteerEntity.getOrganisations());
        return volunteerModel;
    }
}
