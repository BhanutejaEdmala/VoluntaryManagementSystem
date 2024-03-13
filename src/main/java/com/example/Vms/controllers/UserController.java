package com.example.Vms.controllers;

import com.example.Vms.entities.User;
import com.example.Vms.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;

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
        }
