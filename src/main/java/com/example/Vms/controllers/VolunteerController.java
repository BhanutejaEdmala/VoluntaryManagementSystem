package com.example.Vms.controllers;

import com.example.Vms.entities.Event;
import com.example.Vms.entities.Organisation;
import com.example.Vms.entities.Volunteer;
import com.example.Vms.models.EventModel;
import com.example.Vms.models.OrganisationModel;
import com.example.Vms.models.VolunteerModel;
import com.example.Vms.service.serviceimplementationss.UserService;
import com.example.Vms.service.serviceimplementationss.VolunteerService;
import com.example.Vms.service.serviceinterfaces.UserServiceInterface;
import com.example.Vms.service.serviceinterfaces.VolunteerServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/vol")
public class VolunteerController {
    @Autowired
    VolunteerServiceInterface volunteerService;
    @Autowired
    UserServiceInterface userService;
   @PatchMapping("/add")
    public ResponseEntity<?> add(@RequestParam int userId, @RequestParam int organisationId){
       String result = volunteerService.add(userId,organisationId);
       if(result.equals("Registered Successfully"))
           return new ResponseEntity<>(result, HttpStatus.CREATED);
       return new ResponseEntity<>(result,HttpStatus.NOT_FOUND);
    }
    @GetMapping("/totalorg")
    public ResponseEntity<?> totalOrganisations(){
       List<OrganisationModel> organisationList = volunteerService.totalOrganisations();
       if(!(organisationList.isEmpty()))
         return new ResponseEntity<>(organisationList,HttpStatus.FOUND);
       return new ResponseEntity<>("No Organisations Found",HttpStatus.NO_CONTENT);
    }
    @GetMapping("/vieworgbyloc")
    public ResponseEntity<?> viewOrgByLoc(@RequestParam String location){
List<OrganisationModel> organisations = volunteerService.findOrganisationByLoc(location);
if(organisations.isEmpty())
    return new ResponseEntity<>("No Organisations Found With That Location",HttpStatus.NOT_FOUND);
return  new ResponseEntity<>(organisations,HttpStatus.FOUND);
    }
    @GetMapping("/viewvolunteersinevent")
    public ResponseEntity<?> viewVolInEvent(@RequestParam int eventId,@RequestParam int organisationID){
       List<VolunteerModel> volunteers = volunteerService.viewVolunteersInEvent(eventId,organisationID);
       if(null==volunteers)
           return  new ResponseEntity<>("No Data Found",HttpStatus.NOT_FOUND);
       return new ResponseEntity<>(volunteers,HttpStatus.FOUND);
    }
    @GetMapping("/viewmessages")
    public ResponseEntity<?> viewMessage(int volunteerId){
       Set<String> result = volunteerService.showMessages(volunteerId);
       if(null!=result)
            return new ResponseEntity<>(result,HttpStatus.CREATED);
       return new ResponseEntity<>("No Volunteer Found",HttpStatus.NOT_FOUND);
    }
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteVolunteer(int volunteerId){
       String result = volunteerService.leaveOrganisation(volunteerId);
       return ResponseEntity.ok(result);
    }
    @PatchMapping("/sendmessage")
    public ResponseEntity<?> sendMessage(@RequestParam int organisationId, @RequestParam int volunteerId,@RequestParam String message){
       String result = volunteerService.sendMessageToOrganisation(organisationId,volunteerId,message);
       if(null!=result)
           return ResponseEntity.ok(result);
       return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    @GetMapping("/search")
    public ResponseEntity<?> searchEventsBySkill(@RequestParam int organisationId,@RequestParam String skill){
       List<EventModel> events = volunteerService.searchEventsBySkill(organisationId,skill);
       if(!(CollectionUtils.isEmpty(events)))
           return new ResponseEntity<>(events,HttpStatus.FOUND);
       return new ResponseEntity<>("No Events Found",HttpStatus.NOT_FOUND);
    }
    @GetMapping("/getorgbyaddress")
    public ResponseEntity<?> searchOrgByAddress(@RequestParam String address){
       List<OrganisationModel> organisations = volunteerService.searchOrgByAddress(address);
       if(null!=organisations)
           return new ResponseEntity<>(organisations,HttpStatus.FOUND);
       return new ResponseEntity<>("No Organisation Found",HttpStatus.NOT_FOUND);
    }
    @GetMapping("/regevents")
    public ResponseEntity<?> registeredEvents(@RequestParam int volunteerId){
       List<EventModel> events = volunteerService.viewEventsRegistered(volunteerId);
       if(null!=events)
           return new ResponseEntity<>(events,HttpStatus.FOUND);
       return new ResponseEntity<>("You Are Yet To Register In Any Event",HttpStatus.NOT_FOUND);
    }
    @PatchMapping("/completeevent")
    public ResponseEntity<?> completeEvent(@RequestParam int volunteerId,@RequestParam int eventId,@RequestParam int organiationId){
      String result = volunteerService.CompleteEvent(volunteerId,eventId,organiationId);
      return new ResponseEntity<>(result,HttpStatus.OK);
    }
    @GetMapping("/getvolunteer")
    public ResponseEntity<?> get(@RequestParam int volunteerId){
        VolunteerModel volunteer = volunteerService.get(volunteerId);
        if(null!=volunteer)
            return new ResponseEntity<>(volunteer,HttpStatus.FOUND);
        return new ResponseEntity<>("No Volunteer Found",HttpStatus.NOT_FOUND);
    }
    @GetMapping("/eventsregisteredinorganisation")
    public ResponseEntity<?> eventsInAParticulatOrg(@RequestParam int volunteerId,@RequestParam int organisationId){
       List<EventModel> eventModels = volunteerService.eventsRegisteredByVolInOrg(volunteerId,organisationId);
       if(null!=eventModels){
           return  new ResponseEntity<>(eventModels,HttpStatus.OK);
       }
       return new ResponseEntity<>("Check The Details You've Entered",HttpStatus.NOT_FOUND);
    }
}
