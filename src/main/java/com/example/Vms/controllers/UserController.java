package com.example.Vms.controllers;

import com.example.Vms.entities.User;
import com.example.Vms.models.OrganisationModel;
import com.example.Vms.models.UserModel;
import com.example.Vms.service.serviceinterfaces.UserServiceInterface;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserServiceInterface userService;
    @PostMapping("/add")
    public User addUser(@RequestBody @Valid UserModel user){
        return userService.saveUser(user);
        }
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(@RequestParam int userId){
        String result= userService.deleteUser(userId);
        return ResponseEntity.ok(result);
    }
    @GetMapping("/totalorg")
    public ResponseEntity<?> totalOrganisations(){
        List<OrganisationModel> organisationList = userService.totalOrganisations();
        if(!(organisationList.isEmpty()))
            return new ResponseEntity<>(organisationList,HttpStatus.FOUND);
        return new ResponseEntity<>("No Organisations Found",HttpStatus.NO_CONTENT);
    }
    @PatchMapping("/update")
    public ResponseEntity<?> updateUser(@RequestParam int userId,@RequestBody User user){
        String result = userService.updateUser(userId,user);
        return ResponseEntity.ok(result);
    }
    @GetMapping("/regorganisations")
    public ResponseEntity<?> registeredOrganisations(@RequestParam String userName,@RequestParam String password){
      return userService.registeredOrganisations(userName,password);
    }
    @DeleteMapping("/leaveorganisation")
    public ResponseEntity<?> leaveOrganisation(@RequestParam int organisationId,@RequestParam int userId){
        String result = userService.leaveOrgaisation(organisationId,userId);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }
    @GetMapping("/viewcertificates")
    public ResponseEntity<?> viewCertificates(@RequestParam int userId) {
        if (null!=userService.viewCertifications(userId))
            return new ResponseEntity<>(userService.viewCertifications(userId).toString(), HttpStatus.FOUND);
        return new ResponseEntity<>("No Data Found", HttpStatus.NOT_FOUND);
    }
    @GetMapping("/getuser")
    public ResponseEntity<?> getUser(@RequestParam int userId){
        UserModel user = userService.getUser(userId);
        if(null!=user)
            return new ResponseEntity<>(user,HttpStatus.FOUND);
        return new ResponseEntity<>("No User Found",HttpStatus.NOT_FOUND);
    }
    @PatchMapping("/leaveevent")
    public ResponseEntity<?> leaveEvent(@RequestParam int userId,@RequestParam int eid,@RequestParam int organisationId){
        return new ResponseEntity<>(userService.leaveEvent(userId,eid,organisationId),HttpStatus.FOUND);
    }
    @GetMapping("/viewmessages")
    public ResponseEntity<?> viewMessages(@RequestParam String username,@RequestParam String password){
        List<String> messages = userService.viewMessages(username,password);
        if(messages.get(0).equals("Failed"))
            return new ResponseEntity<>("You Are Not Authorised TO Do This",HttpStatus.OK);
        ;
            return new ResponseEntity<>(messages,HttpStatus.FOUND);

    }
}
