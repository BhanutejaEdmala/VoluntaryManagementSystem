package com.example.Vms.service.serviceinterfaces;
import com.example.Vms.entities.User;
import com.example.Vms.models.OrganisationModel;
import com.example.Vms.models.UserModel;

import java.util.List;

public interface UserServiceInterface {
    User save(UserModel user);

    String deleteUser(int uid);

    String updateUser(int uid, User user);

    List<OrganisationModel> registeredOrganisations(int uid);

    String leaveOrgaisation(int oid, int uid);

    Object viewCertifications(int uid);

    String leaveEvent(int uid, int eid, int oid);

    UserModel getUser(int uid);
}
