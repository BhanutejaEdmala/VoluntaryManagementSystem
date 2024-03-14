package com.example.Vms.controllers;

import com.example.Vms.entities.Event;
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
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteVolunteer(int vid){
       String result = volunteerService.leaveOrganisation(vid);
       return ResponseEntity.ok(result);
    }
    @PatchMapping("/sentmessage")
    public ResponseEntity<?> sentMessage(@RequestParam int oid, @RequestParam int vid,@RequestParam String message){
       String result = volunteerService.sentMessageToOrganisation(oid,vid,message);
       if(result!=null)
           return ResponseEntity.ok(result);
       return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    @GetMapping("/search")
    public ResponseEntity<?> searchEventsBySkill(@RequestParam int oid,@RequestParam String skill){
       List<Event> events = volunteerService.searchEventsBySkill(oid,skill);
       if(!(events.isEmpty()))
           return new ResponseEntity<>(events,HttpStatus.FOUND);
       return new ResponseEntity<>("No Events Found",HttpStatus.NOT_FOUND);
    }
    @GetMapping("/getorgbyaddress")
    public ResponseEntity<?> searchOrgByAddress(@RequestParam String address){
       List<Organisation> organisations = volunteerService.searchOrgByAddress(address);
       if(organisations!=null)
           return new ResponseEntity<>(organisations,HttpStatus.FOUND);
       return new ResponseEntity<>("No Organisation Found",HttpStatus.NOT_FOUND);
    }
    @GetMapping("/regevents")
    public ResponseEntity<?> registeredEvents(@RequestParam int vid){
       List<Event> events = volunteerService.viewEventsRegistered(vid);
       if(events!=null)
           return new ResponseEntity<>(events,HttpStatus.FOUND);
       return new ResponseEntity<>("You Are Yet To Register In Any Event",HttpStatus.NOT_FOUND);
    }
    @PatchMapping("/completeevent")
    public ResponseEntity<?> completeEvent(@RequestParam int vid,@RequestParam int eid,@RequestParam int oid){
      String result = volunteerService.CompleteEvent(vid,eid,oid);
      return new ResponseEntity<>(result,HttpStatus.OK);
    }
}
