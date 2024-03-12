package com.example.Vms.controllers;

import com.example.Vms.entities.Organisation;
import com.example.Vms.entities.User;
import com.example.Vms.entities.Volunteer;
import com.example.Vms.models.VolunteerModel;
import com.example.Vms.service.UserService;
import com.example.Vms.service.VolunteerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/vol")
public class VolunteerController {
    @Autowired
    VolunteerService volunteerService;
    @Autowired
    UserService  userService;
   @PatchMapping("/add")
    public ResponseEntity<?> add(@RequestParam int uid, @RequestParam int oid){
       String result = volunteerService.add(uid,oid);
       if(result.equals("Registered Successfully"))
           return new ResponseEntity<>(result, HttpStatus.CREATED);
       return new ResponseEntity<>(result,HttpStatus.NOT_FOUND);
    }
    @GetMapping("/totalorg")
    public ResponseEntity<?> totalOrganisations(){
       List<Organisation> organisationList = volunteerService.totalOrganisations();
       if(!(organisationList.isEmpty()))
       return new ResponseEntity<>(organisationList,HttpStatus.FOUND);
       return new ResponseEntity<>("No Organisations Found",HttpStatus.NO_CONTENT);
    }
    @GetMapping("/vieworgbyloc")
    public ResponseEntity<?> viewOrgByLoc(@RequestParam String location){
List<Organisation> organisations = volunteerService.findOrganisationByLoc(location);
if(organisations.isEmpty())
    return new ResponseEntity<>("No Organisations Found With That Location",HttpStatus.NOT_FOUND);
return  new ResponseEntity<>(organisations,HttpStatus.FOUND);
    }
    @GetMapping("/viewvolunteersinevent")
    public ResponseEntity<?> viewVolInEvent(@RequestParam int eid,@RequestParam int oid){
       List<Volunteer> volunteers = volunteerService.viewVolunteersInEvent(eid,oid);
       if(volunteers==null)
           return  new ResponseEntity<>("No Data Found",HttpStatus.NOT_FOUND);
       return new ResponseEntity<>(volunteers,HttpStatus.FOUND);
    }
    @GetMapping("/viewmessages")
    public ResponseEntity<?> viewMessage(int vid){
       Set<String> result = volunteerService.showMessages(vid);
       if(result!=null)
            return new ResponseEntity<>(result,HttpStatus.CREATED);
       return new ResponseEntity<>("No Volunteer Found",HttpStatus.NOT_FOUND);
    }

}
