package com.example.Vms.entities;
import com.example.Vms.models.EventModel;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int eid;
    private String name;
    private String location;
    private String date;
    private Set<String> skills_good_to_have;
    private String status;
    @JsonIgnore
    @ManyToMany(cascade =  { CascadeType.PERSIST, CascadeType.MERGE },fetch = FetchType.EAGER)
    @JoinTable(
            name = "organisation_event",
            joinColumns = @JoinColumn(name = "eid"),
            inverseJoinColumns = @JoinColumn(name = "oid")
    )
    private List<Organisation> organisations;
    @JsonIgnore
    @ManyToMany(mappedBy = "events",cascade = {CascadeType.PERSIST,CascadeType.MERGE},fetch = FetchType.EAGER)
    private List<Volunteer> volunteerList;

    @Override
    public String toString() {
        return "Event{" +
                "eid=" + eid +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", date='" + date + '\'' +
                ", skills_good_to_have=" + skills_good_to_have +
                '}';
    }
    public Event(EventModel event) {
        this.eid=event.getEid();
        this.name= event.getName();
        this.location=event.getLocation();
        this.date=event.getDate();
        this.organisations=event.getOrganisations();
        this.skills_good_to_have=event.getSkills_good_to_have();
        this.volunteerList=event.getVolunteerList();
    }
}