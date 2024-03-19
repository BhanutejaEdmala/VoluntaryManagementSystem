package com.example.Vms.controllers;

import com.example.Vms.entities.Event;
import com.example.Vms.entities.Organisation;
import com.example.Vms.models.EventModel;
import com.example.Vms.models.OrganisationModel;
import com.example.Vms.service.serviceimplementationss.OrganisationService;
import com.example.Vms.service.serviceinterfaces.OrganisationServiceInterface;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/org")
public class OrganisationController {
    @Autowired
    OrganisationServiceInterface organisationService;
@PostMapping("/saveorg")
    public ResponseEntity<?> add(@RequestBody @Valid OrganisationModel organisation){
    OrganisationModel organisationModel=organisationService.save(organisation);
         return new ResponseEntity<>("Organisation Saved", HttpStatus.CREATED);
}
@PatchMapping("/addevent")
    public ResponseEntity<?> addEvent(@RequestParam int organisationId, @RequestParam int eventId){
    String result =  organisationService.addEvent(organisationId,eventId);
        return new ResponseEntity<>(result,HttpStatus.CREATED);
}
@PatchMapping("/assignevent")
    public ResponseEntity<?> assignEvent(@RequestParam int volunteerId,@RequestParam int eventId,@RequestParam int organisationId){
    String result = organisationService.assignEvent(volunteerId,eventId,organisationId);
    if(null!=result)
        return new ResponseEntity<>(result,HttpStatus.CREATED);
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Check Whether The Details Are Exist In The First Place");
}
@GetMapping("/vieweventsinorg")
    public ResponseEntity<?> viewEventsInOrg(@RequestParam int organisationI){
    List<EventModel> eventList = organisationService.viewEventsInOrganisation(organisationI);
    if(!(CollectionUtils.isEmpty(eventList)))
        return new ResponseEntity<>(eventList,HttpStatus.FOUND);
    return new ResponseEntity<>("No Organisation Found",HttpStatus.NOT_FOUND);
}
@PatchMapping("/sendmessage")
    public ResponseEntity<?> sendMessageToVolunteer(@RequestParam int volunteerId,@RequestParam int organisationId,@RequestParam String message){
    String result = organisationService.sendMessage(volunteerId,organisationId,message);
     if(null!=result)
         return new ResponseEntity<>(result,HttpStatus.ACCEPTED);
     return new ResponseEntity<>("Check For The Existence Of The Details You Are Trying To Access ",HttpStatus.NOT_FOUND);
}
@PatchMapping("/groupmessage")
    public ResponseEntity<?> groupMessageToAll(@RequestParam  int organisationId,@RequestParam String message){
   String result= organisationService.groupMessage(organisationId,message);
    return ResponseEntity.ok(result);
}
@PatchMapping("/suggest")
    public ResponseEntity<?> suggestVolunteers(@RequestParam int eventId,@RequestParam int organisationId){
    String result = organisationService.suggestVolunteers(eventId,organisationId);
    return ResponseEntity.ok(result);
}
@DeleteMapping("/delete")
    public ResponseEntity<?> deleteOrganisation(@RequestParam int organisationId){
    String result = organisationService.removeOrganization(organisationId);
    return ResponseEntity.ok(result);
}
@GetMapping("/viewmessages")
    public ResponseEntity<?> viewMessages(int organisationId){
  List<String> messages =  organisationService.viewMessagesOfVolunteers(organisationId);
  if(null!=messages)
      return ResponseEntity.ok(messages);
  return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Messages Found");
}
@PatchMapping("/updateorg")
public ResponseEntity<?> updateOrganisation(Organisation organisation,int organisationId){
    String result=organisationService.updateOrganisation(organisation,organisationId);
    return new ResponseEntity<>(result,HttpStatus.OK);
}
@PatchMapping("/closeevent")
public ResponseEntity<?> closeevent(@RequestParam int eventId){
    String result= organisationService.closeEvent(eventId);
    return new ResponseEntity<>(result,HttpStatus.OK);
}
@DeleteMapping("/removevolunteer")
    public ResponseEntity<?> removeVolunteer(@RequestParam int volunteerId){
    String result = organisationService.removeVolunteer(volunteerId);
     return new ResponseEntity<>(result,HttpStatus.OK);
}
@GetMapping("/getorg")
    public ResponseEntity<?> getOrganisation(@RequestParam int organisationId){
    OrganisationModel organisation = organisationService.get(organisationId);
    if(null!=organisation)
        return new ResponseEntity<>(organisation,HttpStatus.FOUND);
    return new ResponseEntity<>("No Organisation Found",HttpStatus.NOT_FOUND);
}
@PatchMapping("/closeeventfororg")
    public ResponseEntity<?> closeEventForAnOrganisation(@RequestParam int eventId,@RequestParam int organisationId){
    String result = organisationService.closeEventForOrg(eventId,organisationId);
    return new ResponseEntity<>(result,HttpStatus.OK);
    }
}
