package com.example.Vms.service;

import com.example.Vms.entities.Organisation;
import com.example.Vms.entities.User;

import java.util.List;

public interface UserServiceInterface {
    User save(User user);

    String deleteUser(int uid);

    String updateUser(int uid, User user);

    List<Organisation> registeredOrganisations(int uid);

    String leaveOrgaisation(int oid, int uid);

    Object viewCertifications(int uid);

    String leaveEvent(int uid, int eid, int oid);

    User getUser(int uid);
}
