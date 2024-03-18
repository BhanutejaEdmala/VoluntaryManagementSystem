package com.example.Vms.controllers;

import com.example.Vms.entities.Organisation;
import com.example.Vms.entities.User;
import com.example.Vms.models.OrganisationModel;
import com.example.Vms.models.UserModel;
import com.example.Vms.service.serviceimplementationss.UserService;
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
    UserService userService;
    @PostMapping("/add")
    public User addUser(@RequestBody @Valid UserModel user){
        return userService.save(user);
        }
    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestParam int uid){
        String result= userService.deleteUser(uid);
        return ResponseEntity.ok(result);
    }
    @PatchMapping("/update")
    public ResponseEntity<?> updateUser(@RequestParam int uid,@RequestBody User user){
        String result = userService.updateUser(uid,user);
        return ResponseEntity.ok(result);
    }
    @GetMapping("/regorganisations")
    public ResponseEntity<?> registeredOrganisations(@RequestParam int uid){
      List<OrganisationModel> organisations =  userService.registeredOrganisations(uid);
      if(organisations!=null)
          return new ResponseEntity<>(organisations, HttpStatus.FOUND);
      return new ResponseEntity<>("You Haven't Registered In Any Organisation",HttpStatus.FOUND);
    }
    @DeleteMapping("/leaveorganisation")
    public ResponseEntity<?> leaveOrganisation(@RequestParam int oid,@RequestParam int uid){
        String result = userService.leaveOrgaisation(oid,uid);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }
    @GetMapping("/viewcertificates")
    public ResponseEntity<?> viewCertificates(@RequestParam int uid) {
        if (userService.viewCertifications(uid) != null)
            return new ResponseEntity<>(userService.viewCertifications(uid).toString(), HttpStatus.FOUND);
        return new ResponseEntity<>("No Data Found", HttpStatus.NOT_FOUND);
    }
    @GetMapping("/getuser")
    public ResponseEntity<?> getUser(@RequestParam int uid){
        UserModel user = userService.getUser(uid);
        if(user!=null)
            return new ResponseEntity<>(user,HttpStatus.FOUND);
        return new ResponseEntity<>("No User Found",HttpStatus.NOT_FOUND);
    }
    @PatchMapping("/leaveevent")
    public ResponseEntity<?> leaveEvent(@RequestParam int uid,@RequestParam int eid,@RequestParam int oid){
        return new ResponseEntity<>(userService.leaveEvent(uid,eid,oid),HttpStatus.FOUND);
    }
}
