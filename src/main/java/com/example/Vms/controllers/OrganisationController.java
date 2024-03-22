package com.example.Vms.controllers;

import com.example.Vms.entities.Organisation;
import com.example.Vms.models.EventModel;
import com.example.Vms.models.OrganisationModel;
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
    public ResponseEntity<?> add(@RequestBody @Valid OrganisationModel organisation,@RequestParam String userName,@RequestParam String password){
    String result=organisationService.save(organisation,userName,password);
         return new ResponseEntity<>(result, HttpStatus.CREATED);
}
@PatchMapping("/assignevent")
    public ResponseEntity<?> assignEvent(@RequestParam String username,@RequestParam String password,@RequestParam int volunteerId,@RequestParam int eventId,@RequestParam int organisationId){
    String result = organisationService.assignEvent(username,password,volunteerId,eventId,organisationId);
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
    public ResponseEntity<?> sendMessageToVolunteer(@RequestParam String username,@RequestParam String password,@RequestParam int volunteerId,@RequestParam int organisationId,@RequestParam String message){
    String result = organisationService.sendMessage(username,password,volunteerId,organisationId,message);
     if(null!=result)
         return new ResponseEntity<>(result,HttpStatus.ACCEPTED);
     return new ResponseEntity<>("Check For The Existence Of The Details You Are Trying To Access ",HttpStatus.NOT_FOUND);
}
@PatchMapping("/groupmessage")
    public ResponseEntity<?> groupMessageToAll(@RequestParam String username,@RequestParam String password,@RequestParam  int organisationId,@RequestParam String message){
   String result= organisationService.groupMessage(username,password,organisationId,message);
    return ResponseEntity.ok(result);
}
@PatchMapping("/suggest")
    public ResponseEntity<?> suggestVolunteers(@RequestParam String username,@RequestParam String password,@RequestParam int eventId,@RequestParam int organisationId){
    String result = organisationService.suggestVolunteers(username,password,eventId,organisationId);
    return ResponseEntity.ok(result);
}
@DeleteMapping("/delete")
    public ResponseEntity<?> deleteOrganisation(@RequestParam String username,@RequestParam String password,@RequestParam int organisationId){
    String result = organisationService.removeOrganization(username,password,organisationId);
    return ResponseEntity.ok(result);
}
@GetMapping("/viewmessages")
    public ResponseEntity<?> viewMessages(@RequestParam String username,@RequestParam String password,int organisationId){
  List<String> messages =  organisationService.viewMessagesOfVolunteers(username,password,organisationId);
  if(null!=messages)
      return ResponseEntity.ok(messages);
  return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Messages Found");
}
@PatchMapping("/updateorg")
public ResponseEntity<?> updateOrganisation(@RequestParam String username,@RequestParam String password,Organisation organisation,int organisationId){
    String result=organisationService.updateOrganisation(username,password,organisation,organisationId);
    return new ResponseEntity<>(result,HttpStatus.OK);
}
@PatchMapping("/closeevent")
public ResponseEntity<?> closeevent(@RequestParam int eventId){
    String result= organisationService.closeEvent(eventId);
    return new ResponseEntity<>(result,HttpStatus.OK);
}
@DeleteMapping("/removevolunteer")
    public ResponseEntity<?> removeVolunteer(@RequestParam String username,@RequestParam String password,@RequestParam int organisationId,@RequestParam int volunteerId){
    String result = organisationService.removeVolunteer(username,password,organisationId,volunteerId);
     return new ResponseEntity<>(result,HttpStatus.OK);
}
@GetMapping("/getorg")
    public ResponseEntity<?> getOrganisation(@RequestParam int organisationId){
    OrganisationModel organisation = organisationService.getOrganisation(organisationId);
    if(null!=organisation)
        return new ResponseEntity<>(organisation,HttpStatus.FOUND);
    return new ResponseEntity<>("No Organisation Found",HttpStatus.NOT_FOUND);
}
@PatchMapping("/closeeventfororg")
    public ResponseEntity<?> closeEventForAnOrganisation(@RequestParam String username,@RequestParam String password,@RequestParam int eventId,@RequestParam int organisationId){
    String result = organisationService.closeEventForOrg(username,password,eventId,organisationId);
    return new ResponseEntity<>(result,HttpStatus.OK);
    }
   @PostMapping("/addEventInOrg")
    public ResponseEntity<?> addEventInOrg(@RequestBody @Valid EventModel eventModel,@RequestParam int oid,@RequestParam String username,@RequestParam String password){
        System.out.println(eventModel);
       String result = organisationService.addEventInOrganisation(username,password,eventModel,oid);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }
    @PatchMapping("/acceptrequests")
    public ResponseEntity<?> acceptJoiningRequests(@RequestParam String username,@RequestParam String password,@RequestParam int organisationId){
    String result=organisationService.approveJoinRequests(username,password,organisationId);
    return new ResponseEntity<>(result,HttpStatus.OK);
    }
    @PatchMapping("/acceptrequest")
    public ResponseEntity<?> acceptJoiningRequest(@RequestParam String username,@RequestParam String password,@RequestParam String user,@RequestParam int organisationId){
        String result=organisationService.approveSingleRequest(username,password,user,organisationId);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }
    @PatchMapping("/rejectrequests")
    public ResponseEntity<?> rejectRequests(@RequestParam String username,@RequestParam String password,@RequestParam int organisationId){
    String result = organisationService.rejectingRequests(username,password,organisationId);
    return new ResponseEntity<>(result,HttpStatus.OK);
    }
}
