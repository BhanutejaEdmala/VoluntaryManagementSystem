package com.example.Vms.models;
import com.example.Vms.entities.Event;
import com.example.Vms.entities.Organisation;
import com.example.Vms.entities.Volunteer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EventModel {
    private int eid;
    private String name;
    private String location;
    private String date;
    private Set<String> skills_good_to_have;
    private List<Organisation> organisations;
    private List<Volunteer> volunteerList;
    @Override
    public String toString() {
        return "EventModel{" +
                "eid=" + eid +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", date='" + date + '\'' +
                ", skills_good_to_have=" + skills_good_to_have +
                '}';
    }

    public EventModel(Event event) {
        this.eid=event.getEid();
        this.name= event.getName();
        this.location=event.getLocation();
        this.date=event.getDate();
        this.organisations=event.getOrganisations();
        this.skills_good_to_have=event.getSkills_good_to_have();
        this.volunteerList=event.getVolunteerList();
    }

}

