package com.example.Vms.models;
import com.example.Vms.entities.Event;
import com.example.Vms.entities.Organisation;
import com.example.Vms.entities.Volunteer;
import com.example.Vms.validation.MyValid;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EventModel {
    private int eid;
    @NotEmpty(message = "name shouln't be empty")
    private String name;
    @NotEmpty(message = "location shouldn't be empty")
    private String location;
    @NotEmpty(message = "date shouldn't be empty")
    private String date;
    @MyValid
    private Set<String> skills_good_to_have = new LinkedHashSet<>();
    private List<Organisation> organisations = new ArrayList<>();
    private List<Volunteer> volunteerList = new ArrayList<>();
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

