package com.example.Vms.service.serviceimplementations;

import com.example.Vms.conversions.EntityToModel;
import com.example.Vms.conversions.ModelToEntity;
import com.example.Vms.entities.Event;
import com.example.Vms.entities.Organisation;
import com.example.Vms.entities.User;
import com.example.Vms.entities.Volunteer;
import com.example.Vms.models.OrganisationModel;
import com.example.Vms.models.UserModel;
import com.example.Vms.repositories.EventRepo;
import com.example.Vms.repositories.OrganisationRepo;
import com.example.Vms.repositories.UserRepo;
import com.example.Vms.repositories.VolunteerRepo;
import com.example.Vms.service.serviceinterfaces.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class UserService  implements UserServiceInterface {
    @Autowired
    UserRepo userRepo;
    @Autowired
    VolunteerRepo volunteerRepo;
    @Autowired
    OrganisationRepo organisationRepo;
    @Autowired
    VolunteerService volunteerService;
    @Autowired
    private PasswordEncoder passwordEncoder;
@Autowired
    EventRepo eventRepo;
@Autowired
ModelToEntity modelToEntity;
@Autowired
EntityToModel entityToModel;

    public User save(UserModel user) {
       User user1= modelToEntity.userModelToEntity(user);
        user1.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user1);
    }

    public String deleteUser(int userId) {
        User user = userRepo.findById(userId).orElse(null);
        if (null!=user) {
            // Retrieve related entities
            List<Volunteer> volunteers = volunteerRepo.findAll();
            List<Organisation> organisations = organisationRepo.findAll();
            volunteers.stream().filter(i -> i.getUser().equals(user)).map(Volunteer::getVid).forEach(volunteerService::leaveOrganisation);
            organisations.stream().filter(i -> i.getUsers().contains(user)).forEach(i -> i.getUsers().remove(user));
            organisationRepo.saveAll(organisations);
            user.getVolunteers().clear();
            user.getOrganisations().clear();
            userRepo.delete(user);
            return "User Deleted";
        }
        return "User Not Found";
    }

    public String updateUser(int userId, User user) {
        User user1 = userRepo.findById(userId).orElse(null);
        if (null!=user1) {
            user1.setName(user.getName());
            user1.setPassword(passwordEncoder.encode(user.getPassword()));
            user1.setAddress(user.getAddress());
            user1.getSkills().addAll(user.getSkills());
            userRepo.save(user1);
            List<Volunteer> volunteers = user1.getVolunteers();
            volunteers.forEach(i -> {
                i.setName(user.getName());
                i.setAddress(user.getAddress());
                i.getSkills().addAll(user.getSkills());
                volunteerRepo.save(i);
            });
            return "Updated";
        }
        return "User Doesn't Exist";
    }

    public List<OrganisationModel> registeredOrganisations(int userId) {
        User user = userRepo.findById(userId).orElse(null);
        if (null!=user) {
            return user.getOrganisations().stream().map(entityToModel::organisationToOrganisationModel).toList();
        }
        return null;
    }

    public String leaveOrgaisation(int organisationId, int userId) {
        Organisation organisation = organisationRepo.findById(organisationId).orElse(null);
        User user = userRepo.findById(userId).orElse(null);
        if (null!=organisation && null!=user) {
            if (organisation.getUsers().contains(user)) {
                List<Volunteer> volunteers = volunteerRepo.findAll();
                Volunteer volunteer = volunteers.stream().filter(i -> i.getUser().equals(user) && i.getOrganisations().contains(organisation)).findFirst().orElse(null);
                assert null!=volunteer ;
                volunteerService.leaveOrganisation(volunteer.getVid());
                organisation.getUsers().remove(user);
                organisationRepo.save(organisation);
                user.getOrganisations().remove(organisation);
                userRepo.save(user);
                return "Successfully Left";
            }
            return "You Haven't Registered In This Organisation";
        }
        return "No Data Found";
    }

    public Object viewCertifications(int userId) {
        User user = userRepo.findById(userId).orElse(null);
        if (null!=user ) {
            List<String> certificates = user.getCertificates();
            if (!(CollectionUtils.isEmpty(certificates)))
                return certificates;
            return "You Are Yet To Receive A Certificate";
        }
        return null;
    }
    public String leaveEvent(int userId,int eventId,int organisatonId){
        User user = userRepo.findById(userId).orElse(null);
        Event event = eventRepo.findById(eventId).orElse(null);
        Organisation organisation = organisationRepo.findById(organisatonId).orElse(null);
        if(null!=user&&null!=event&&null!=organisation){
            List<Volunteer> volunteers = volunteerRepo.findAll();
           Volunteer volunteer= volunteers.stream().filter(i->i.getUser().equals(user)&&i.getOrganisations().contains(organisation)).findFirst().orElse(null);
           if(null!=volunteer&&volunteer.getEvents().contains(event)) {
               volunteer.getEvents().remove(event);
               event.getVolunteerList().remove(volunteer);
               eventRepo.save(event);
               volunteerRepo.save(volunteer);
               return "You Left The Event";
           }
           return "Check Whether You Registered In This Organisation Or In This Event";
        }
        return "Make Sure To Enter Correct Details";
    }
    public UserModel getUser(int userId){
      User user = userRepo.findById(userId).orElse(null);
      if(null!=user){
          return entityToModel.userEntityToModel(user);}
      return null;
    }
}
