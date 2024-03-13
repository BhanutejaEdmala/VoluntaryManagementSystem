package com.example.Vms.service;

import com.example.Vms.entities.Organisation;
import com.example.Vms.entities.User;
import com.example.Vms.entities.Volunteer;
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
public class UserService {
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

    public User save(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }
    public String deleteUser(int uid) {
        User user = userRepo.findById(uid).orElse(null);
        if (user != null) {
            // Retrieve related entities
            List<Volunteer> volunteers = volunteerRepo.findAll();
            List<Organisation> organisations = organisationRepo.findAll();
            volunteers.stream().filter(i->i.getUser().equals(user)).map(Volunteer::getVid).forEach(volunteerService::leaveOrganisation);
            organisations.stream().filter(i->i.getUsers().contains(user)).forEach(i->i.getUsers().remove(user));
            organisationRepo.saveAll(organisations);
            user.getVolunteers().clear();
            user.getOrganisations().clear();
            userRepo.delete(user);
            return "User Deleted";
        }
        return "User Not Found";
    }
    public String updateUser(int uid,User user){
        User user1 =  userRepo.findById(uid).orElse(null);
        if(user1!=null) {
            user1.setName(user.getName());
            user1.setPassword(passwordEncoder.encode(user.getPassword()));
            user1.setAddress(user.getAddress());
            user1.getSkills().addAll(user.getSkills());
            userRepo.save(user1);
            List<Volunteer> volunteers = user1.getVolunteers();
            volunteers.forEach(i->{
                i.setName(user.getName());
                i.setAddress(user.getAddress());
                i.getSkills().addAll(user.getSkills());
                volunteerRepo.save(i);
            });
            return "Updated";
        }
        return "User Doesn't Exist";
    }
    public List<Organisation> registeredOrganisations(int uid){
        User user = userRepo.findById(uid).orElse(null);
        if(user!=null){
            return user.getOrganisations();
        }
        return null;
    }
}
