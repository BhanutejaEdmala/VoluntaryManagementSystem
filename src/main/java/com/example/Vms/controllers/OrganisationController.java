package com.example.Vms.controllers;

import com.example.Vms.entities.Event;
import com.example.Vms.entities.Organisation;
import com.example.Vms.models.EventModel;
import com.example.Vms.models.OrganisationModel;
import com.example.Vms.service.OrganisationService;
import jakarta.validation.Valid;
import org.aspectj.weaver.ast.Or;
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
@PostMapping("/save")
    public ResponseEntity<?> add(@RequestBody @Valid Organisation organisation){
    OrganisationModel organisationModel=organisationService.save(organisation);
    System.out.println("hi");
         return new ResponseEntity<>("Organisation Saved", HttpStatus.CREATED);
}
@PatchMapping("/addevent")
    public ResponseEntity<?> addEvent(@RequestParam int oid, @RequestParam int eid){
    EventModel eventModel = organisationService.addEvent(oid,eid);
    if(eventModel!=null)
        return new ResponseEntity<>(eventModel,HttpStatus.CREATED);
    return new ResponseEntity<>("No Data Found",HttpStatus.NOT_FOUND);
}
@PatchMapping("/assignevent")
    public ResponseEntity<?> assignEvent(@RequestParam int vid,@RequestParam int eid,@RequestParam int oid){
    String result = organisationService.assignEvent(vid,eid,oid);
    if(result!=null)
        return new ResponseEntity<>(result,HttpStatus.CREATED);
    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
}
@GetMapping("/vieweventsinorg")
    public ResponseEntity<?> viewEventsInOrg(@RequestParam int oid){
    List<Event> eventList = organisationService.viewEventsInOrganisation(oid);
    if(!(eventList==null))
        return new ResponseEntity<>(eventList,HttpStatus.FOUND);
    return new ResponseEntity<>("No Data Found",HttpStatus.NOT_FOUND);
}
@PatchMapping("/sentmessage")
    public ResponseEntity<?> sentMessage(@RequestParam int vid,@RequestParam int oid,@RequestParam String message){
    String result = organisationService.sentMessage(vid,oid,message);
     if(result!=null)
         return new ResponseEntity<>(result,HttpStatus.ACCEPTED);
     return new ResponseEntity<>("No Data Found",HttpStatus.NOT_FOUND);
}
@PatchMapping("/groupmessage")
    public ResponseEntity<?> groupMessage(@RequestParam  int oid,@RequestParam String message){
   String result= organisationService.groupMessage(oid,message);
    return ResponseEntity.ok(null);
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
  return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
}
@DeleteMapping("/removevolunteer")
    public ResponseEntity<?> removeVolunteer(@RequestParam int vid){
    String result = organisationService.removeVolunteer(vid);
     return new ResponseEntity<>(result,HttpStatus.OK);
}
@GetMapping("/get")
    public ResponseEntity<?> get(@RequestParam int oid){
    Organisation organisation = organisationService.get(oid);
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
