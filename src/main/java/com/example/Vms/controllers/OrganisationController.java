package com.example.Vms.controllers;

import com.example.Vms.entities.Event;
import com.example.Vms.entities.Organisation;
import com.example.Vms.models.EventModel;
import com.example.Vms.models.OrganisationModel;
import com.example.Vms.service.serviceimplementationss.OrganisationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/org")
public class OrganisationController {
    @Autowired
    OrganisationService organisationService;
@PostMapping("/saveorg")
    public ResponseEntity<?> add(@RequestBody @Valid OrganisationModel organisation){
    OrganisationModel organisationModel=organisationService.save(organisation);
         return new ResponseEntity<>("Organisation Saved", HttpStatus.CREATED);
}
@PatchMapping("/addevent")
    public ResponseEntity<?> addEvent(@RequestParam int oid, @RequestParam int eid){
    String result =  organisationService.addEvent(oid,eid);
        return new ResponseEntity<>(result,HttpStatus.CREATED);
}
@PatchMapping("/assignevent")
    public ResponseEntity<?> assignEvent(@RequestParam int vid,@RequestParam int eid,@RequestParam int oid){
    String result = organisationService.assignEvent(vid,eid,oid);
    if(result!=null)
        return new ResponseEntity<>(result,HttpStatus.CREATED);
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Check Whether The Details Are Exist In The First Place");
}
@GetMapping("/vieweventsinorg")
    public ResponseEntity<?> viewEventsInOrg(@RequestParam int oid){
    List<EventModel> eventList = organisationService.viewEventsInOrganisation(oid);
    if(!(eventList.isEmpty()))
        return new ResponseEntity<>(eventList,HttpStatus.FOUND);
    return new ResponseEntity<>("No Organisation Found",HttpStatus.NOT_FOUND);
}
@PatchMapping("/sendmessage")
    public ResponseEntity<?> sendMessage(@RequestParam int vid,@RequestParam int oid,@RequestParam String message){
    String result = organisationService.sendMessage(vid,oid,message);
     if(result!=null)
         return new ResponseEntity<>(result,HttpStatus.ACCEPTED);
     return new ResponseEntity<>("Check For The Existence Of The Details You Are Trying To Access ",HttpStatus.NOT_FOUND);
}
@PatchMapping("/groupmessage")
    public ResponseEntity<?> groupMessage(@RequestParam  int oid,@RequestParam String message){
   String result= organisationService.groupMessage(oid,message);
    return ResponseEntity.ok(result);
}
@PatchMapping("/suggest")
    public ResponseEntity<?> suggestVolunteers(@RequestParam int eid,@RequestParam int oid){
    String result = organisationService.suggestVolunteers(eid,oid);
    return ResponseEntity.ok(result);
}
@DeleteMapping("/delete")
    public ResponseEntity<?> deleteOrganisation(@RequestParam int oid){
    String result = organisationService.removeOrganization(oid);
    return ResponseEntity.ok(result);
}
@GetMapping("/viewmessages")
    public ResponseEntity<?> viewMessages(int oid){
  List<String> messages =  organisationService.viewMessagesOfVolunteers(oid);
  if(messages!=null)
      return ResponseEntity.ok(messages);
  return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Messages Found");
}
@PatchMapping("/closeevent")
public ResponseEntity<?> closeevent(@RequestParam int eid){
    String result= organisationService.closeEvent(eid);
    return new ResponseEntity<>(result,HttpStatus.OK);
}
@DeleteMapping("/removevolunteer")
    public ResponseEntity<?> removeVolunteer(@RequestParam int vid){
    String result = organisationService.removeVolunteer(vid);
     return new ResponseEntity<>(result,HttpStatus.OK);
}
@GetMapping("/getorg")
    public ResponseEntity<?> get(@RequestParam int oid){
    OrganisationModel organisation = organisationService.get(oid);
    if(organisation!=null)
        return new ResponseEntity<>(organisation,HttpStatus.FOUND);
    return new ResponseEntity<>("No Organisation Found",HttpStatus.NOT_FOUND);
}
@PatchMapping("/closeeventfororg")
    public ResponseEntity<?> closeEvent(@RequestParam int eid,@RequestParam int oid){
    String result = organisationService.closeEventForOrg(eid,oid);
    return new ResponseEntity<>(result,HttpStatus.OK);
    }
}
