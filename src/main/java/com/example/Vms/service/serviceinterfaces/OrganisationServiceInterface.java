package com.example.Vms.service.serviceinterfaces;

import com.example.Vms.entities.Organisation;
import com.example.Vms.models.EventModel;
import com.example.Vms.models.OrganisationModel;

import java.util.List;

public interface OrganisationServiceInterface {
    OrganisationModel save(OrganisationModel organisation);

    String addEvent(int organisationId, int eventId);

    String assignEvent(int voluteerId, int eventId, int organisationId);

    List<EventModel> viewEventsInOrganisation(int organisationId);

    String sendMessage(int volunteerId, int organisationId, String message);

    String groupMessage(int organisationId, String message);

    String suggestVolunteers(int eventId, int organisationId);

    String removeOrganization(int organisationId);

    String updateOrganisation(Organisation organisation, int organisationId);

    String closeEventForOrg(int eventId, int organisationId);

    String removeVolunteer(int volunteerId);

    List<String> viewMessagesOfVolunteers(int organisationId);

    OrganisationModel get(int organisationId);
    boolean timingsCompare(String[] newTimings,String[] existTimings);
     String closeEvent(int eventId);
}
