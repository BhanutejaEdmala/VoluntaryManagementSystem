package com.example.Vms.controllers;

import com.example.Vms.entities.Organisation;
import com.example.Vms.entities.User;
import com.example.Vms.models.OrganisationModel;
import com.example.Vms.models.UserModel;
import com.example.Vms.service.serviceimplementationss.UserService;
import com.example.Vms.service.serviceinterfaces.UserServiceInterface;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserServiceInterface userService;
    @PostMapping("/add")
    public User addUser(@RequestBody @Valid UserModel user){
        return userService.save(user);
        }
    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestParam int userId){
        String result= userService.deleteUser(userId);
        return ResponseEntity.ok(result);
    }
    @PatchMapping("/update")
    public ResponseEntity<?> updateUser(@RequestParam int userId,@RequestBody User user){
        String result = userService.updateUser(userId,user);
        return ResponseEntity.ok(result);
    }
    @GetMapping("/regorganisations")
    public ResponseEntity<?> registeredOrganisations(@RequestParam int userId){
      List<OrganisationModel> organisations =  userService.registeredOrganisations(userId);
      if(organisations!=null)
          return new ResponseEntity<>(organisations, HttpStatus.FOUND);
      return new ResponseEntity<>("You Haven't Registered In Any Organisation",HttpStatus.FOUND);
    }
    @DeleteMapping("/leaveorganisation")
    public ResponseEntity<?> leaveOrganisation(@RequestParam int organisationId,@RequestParam int userId){
        String result = userService.leaveOrgaisation(organisationId,userId);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }
    @GetMapping("/viewcertificates")
    public ResponseEntity<?> viewCertificates(@RequestParam int userId) {
        if (userService.viewCertifications(userId) != null)
            return new ResponseEntity<>(userService.viewCertifications(userId).toString(), HttpStatus.FOUND);
        return new ResponseEntity<>("No Data Found", HttpStatus.NOT_FOUND);
    }
    @GetMapping("/getuser")
    public ResponseEntity<?> getUser(@RequestParam int userId){
        UserModel user = userService.getUser(userId);
        if(user!=null)
            return new ResponseEntity<>(user,HttpStatus.FOUND);
        return new ResponseEntity<>("No User Found",HttpStatus.NOT_FOUND);
    }
    @PatchMapping("/leaveevent")
    public ResponseEntity<?> leaveEvent(@RequestParam int userId,@RequestParam int eid,@RequestParam int organisationId){
        return new ResponseEntity<>(userService.leaveEvent(userId,eid,organisationId),HttpStatus.FOUND);
    }
}
