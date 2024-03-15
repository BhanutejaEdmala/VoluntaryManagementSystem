package com.example.Vms.service;

import com.example.Vms.entities.Event;
import com.example.Vms.entities.Organisation;
import com.example.Vms.entities.Volunteer;

import java.util.List;
import java.util.Set;

public interface VolunteerServiceInterface {
    String add(int uid, int oid);

    List<Organisation> totalOrganisations();

    List<Organisation> findOrganisationByLoc(String location);

    List<Volunteer> viewVolunteersInEvent(int eid, int oid);

    Set<String> showMessages(int vid);

    String CompleteEvent(int vid, int eid, int oid);

    String leaveOrganisation(int vid);

    List<Event> viewEventsRegistered(int vid);

    String sendMessageToOrganisation(int oid, int vid, String message);

    List<Event> searchEventsBySkill(int oid, String skill);

    List<Organisation> searchOrgByAddress(String address);

    Volunteer get(int vid);
}
