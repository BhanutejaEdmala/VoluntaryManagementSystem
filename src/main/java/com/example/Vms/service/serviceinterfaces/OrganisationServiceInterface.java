package com.example.Vms.service.serviceinterfaces;

import com.example.Vms.entities.Organisation;
import com.example.Vms.models.EventModel;
import com.example.Vms.models.OrganisationModel;

import java.util.List;

public interface OrganisationServiceInterface {
    OrganisationModel save(OrganisationModel organisation);

    String addEvent(int oid, int eid);

    String assignEvent(int vid, int eid, int oid);

    List<EventModel> viewEventsInOrganisation(int oid);

    String sendMessage(int vid, int oid, String message);

    String groupMessage(int oid, String message);

    String suggestVolunteers(int eid, int oid);

    String removeOrganization(int oid);

    String updateOrganisation(Organisation organisation, int oid);

    String closeEventForOrg(int eid, int oid);

    String removeVolunteer(int vid);

    List<String> viewMessagesOfVolunteers(int oid);

    OrganisationModel get(int oid);
    boolean timingsCompare(String[] newTimings,String[] existTimings);
     String closeEvent(int eventId);
}
