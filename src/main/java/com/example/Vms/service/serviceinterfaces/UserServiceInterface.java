package com.example.Vms.service.serviceinterfaces;
import com.example.Vms.entities.User;
import com.example.Vms.models.OrganisationModel;
import com.example.Vms.models.UserModel;

import java.util.List;

public interface UserServiceInterface {
    User save(UserModel user);

    String deleteUser(int userId);

    String updateUser(int userId, User user);

    List<OrganisationModel> registeredOrganisations(int userId);

    String leaveOrgaisation(int organisationId, int userId);

    Object viewCertifications(int userId);

    String leaveEvent(int userId, int eventId, int organisationId);

    UserModel getUser(int userId);
    List<String> viewMessages(String username);
}
