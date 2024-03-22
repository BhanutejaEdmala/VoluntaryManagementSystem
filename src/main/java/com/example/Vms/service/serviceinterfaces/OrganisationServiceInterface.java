package com.example.Vms.service.serviceinterfaces;

import com.example.Vms.entities.Organisation;
import com.example.Vms.models.EventModel;
import com.example.Vms.models.OrganisationModel;

import java.util.List;

public interface OrganisationServiceInterface {
   String saveOrganisation(OrganisationModel organisation,String userName,String Password);

    String addEvent(String username,String password,int organisationId, int eventId);

    String assignEvent(String userName,String password,int voluteerId, int eventId, int organisationId);

    List<EventModel> viewEventsInOrganisation(int organisationId);

    String sendMessage(String username,String password,int volunteerId, int organisationId, String message);

    String groupMessage(String userName,String password,int organisationId, String message);

    String suggestVolunteers(String userName,String password,int eventId, int organisationId);

    String removeOrganization(String userName,String password,int organisationId);

    String updateOrganisation(String userName,String password,Organisation organisation, int organisationId);

    String closeEventForOrg(String username,String password,int eventId, int organisationId);

    String removeVolunteer(String username,String password,int organisationId,int volunteerId);

    List<String> viewMessagesFromVolunteers(String userName,String password,int organisationId);

    OrganisationModel getOrganisation(int organisationId);
    boolean timingsCompare(String[] newTimings,String[] existTimings);
     String closeEvent(int eventId);
    String addEventInOrganisation(String username,String password,EventModel eventModel ,int oid);
 String approveJoinRequests(String userName,String password,int organisationId);
 String approveSingleRequest(String username,String password,String user,int organisationId);
 String rejectingRequests(String username,String password,int organisationId);
}
