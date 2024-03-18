package com.example.Vms.service.serviceinterfaces;

import com.example.Vms.entities.Event;
import com.example.Vms.entities.Organisation;
import com.example.Vms.entities.Volunteer;
import com.example.Vms.models.EventModel;
import com.example.Vms.models.OrganisationModel;
import com.example.Vms.models.VolunteerModel;

import java.util.List;
import java.util.Set;

public interface VolunteerServiceInterface {
    String add(int uid, int oid);

    List<OrganisationModel> totalOrganisations();

    List<OrganisationModel> findOrganisationByLoc(String location);

    List<VolunteerModel> viewVolunteersInEvent(int eid, int oid);

    Set<String> showMessages(int vid);

    String CompleteEvent(int vid, int eid, int oid);

    String leaveOrganisation(int vid);

    List<EventModel> viewEventsRegistered(int vid);

    String sendMessageToOrganisation(int oid, int vid, String message);

    List<EventModel> searchEventsBySkill(int oid, String skill);

    List<OrganisationModel> searchOrgByAddress(String address);

    VolunteerModel get(int vid);
    public List<EventModel> eventsRegisteredByVolInOrg(int volunteerId,int organisationId);
}
