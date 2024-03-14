package com.example.Vms.controllers;

import com.example.Vms.entities.Organisation;
import com.example.Vms.entities.User;
import com.example.Vms.service.UserService;
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
    public User addUser(@RequestBody @Valid User user){
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
      List<Organisation> organisations =  userService.registeredOrganisations(uid);
      if(organisations!=null)
          return new ResponseEntity<>(organisations, HttpStatus.FOUND);
      return new ResponseEntity<>("You Haven't Registered In Any Organisation",HttpStatus.FOUND);
    }
    @DeleteMapping("/leaveorganisation")
    public ResponseEntity<?> leaveOrganisation(@RequestParam int oid,@RequestParam int uid){
        String result = userService.leaveOrgaisation(oid,uid);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }
}
