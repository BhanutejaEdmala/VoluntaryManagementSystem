package com.example.Vms.service;

import com.example.Vms.entities.Event;
import com.example.Vms.entities.Organisation;
import com.example.Vms.entities.User;
import com.example.Vms.entities.Volunteer;
import com.example.Vms.repositories.EventRepo;
import com.example.Vms.repositories.OrganisationRepo;
import com.example.Vms.repositories.UserRepo;
import com.example.Vms.repositories.VolunteerRepo;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService  implements UserServiceInterface{
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
    public User save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    public String deleteUser(int uid) {
        User user = userRepo.findById(uid).orElse(null);
        if (user != null) {
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

    public String updateUser(int uid, User user) {
        User user1 = userRepo.findById(uid).orElse(null);
        if (user1 != null) {
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

    public List<Organisation> registeredOrganisations(int uid) {
        User user = userRepo.findById(uid).orElse(null);
        if (user != null) {
            return user.getOrganisations();
        }
        return null;
    }

    public String leaveOrgaisation(int oid, int uid) {
        Organisation organisation = organisationRepo.findById(oid).orElse(null);
        User user = userRepo.findById(uid).orElse(null);
        if (organisation != null && user != null) {
            if (organisation.getUsers().contains(user)) {
                List<Volunteer> volunteers = volunteerRepo.findAll();
                Volunteer volunteer = volunteers.stream().filter(i -> i.getUser().equals(user) && i.getOrganisations().contains(organisation)).findFirst().orElse(null);
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

    public Object viewCertifications(int uid) {
        User user = userRepo.findById(uid).orElse(null);
        if (user != null) {
            List<String> certificates = user.getCertificates();
            if (!(certificates .isEmpty()))
                return certificates;
            return "You Are Yet To Receive A Certificate";
        }
        return null;
    }
    public String leaveEvent(int uid,int eid,int oid){
        User user = userRepo.findById(uid).orElse(null);
        Event event = eventRepo.findById(eid).orElse(null);
        Organisation organisation = organisationRepo.findById(oid).orElse(null);
        if(user!=null&&event!=null&&organisation!=null){
            List<Volunteer> volunteers = volunteerRepo.findAll();
           Volunteer volunteer= volunteers.stream().filter(i->i.getUser().equals(user)&&i.getOrganisations().contains(organisation)).findFirst().orElse(null);
           if(volunteer!=null&&!(volunteer.getEvents().contains(event))) {
               volunteer.getEvents().remove(event);
               event.getVolunteerList().remove(volunteer);
               eventRepo.save(event);
               volunteerRepo.save(volunteer);
               return "You Left The Event";
           }
           return "Check Wheather You Registered In This Organisation Or In This Event";
        }
        return "Data Not Found";
    }
    public User getUser(int uid){
      User user = userRepo.findById(uid).orElse(null);
      if(user!=null)
          return user;
      return null;
    }
}
