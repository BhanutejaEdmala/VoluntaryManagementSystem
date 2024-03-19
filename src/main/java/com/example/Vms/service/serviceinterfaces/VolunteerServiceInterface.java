package com.example.Vms.service.serviceinterfaces;
import com.example.Vms.models.EventModel;
import com.example.Vms.models.OrganisationModel;
import com.example.Vms.models.VolunteerModel;

import java.util.List;
import java.util.Set;

public interface VolunteerServiceInterface {
    String add(int userId, int organisationId);

    List<OrganisationModel> totalOrganisations();

    List<OrganisationModel> findOrganisationByLoc(String location);

    List<VolunteerModel> viewVolunteersInEvent(int eventId, int organisationId);

    Set<String> showMessages(int volunteerId);

    String CompleteEvent(int volunteerId, int eventId, int organisationId);

    String leaveOrganisation(int volunteerId);

    List<EventModel> viewEventsRegistered(int volunteerId);

    String sendMessageToOrganisation(int organisationId, int volunteerId, String message);

    List<EventModel> searchEventsBySkill(int organisationId, String skill);

    List<OrganisationModel> searchOrgByAddress(String address);

    VolunteerModel get(int volunteerId);
    List<EventModel> eventsRegisteredByVolInOrg(int volunteerId,int organisationId);
}
