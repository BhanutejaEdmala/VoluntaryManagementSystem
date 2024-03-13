package com.example.Vms.entities;
import com.example.Vms.models.VolunteerModel;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Volunteer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int vid;
    private String name;
    private String address;
    private Set<String> messages = new LinkedHashSet<>();
    private Set<String> skills;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "uid")
    private User user;
    @ManyToMany(cascade =  { CascadeType.PERSIST, CascadeType.MERGE },fetch = FetchType.EAGER)
    @JoinTable(
            name = "organisation_volunteer",
            joinColumns = @JoinColumn(name = "vid"),
            inverseJoinColumns = @JoinColumn(name = "oid")
    )
    @JsonIgnore
   // @JsonBackReference
    private Set<Organisation> organisations = new LinkedHashSet<>();
    @ManyToMany(cascade =  { CascadeType.PERSIST, CascadeType.MERGE,CascadeType.REMOVE },fetch = FetchType.EAGER)
    @JoinTable(
            name = "event_volunteer",
            joinColumns = @JoinColumn(name = "vid"),
            inverseJoinColumns = @JoinColumn(name = "eid")
    )
    @JsonIgnore
    @JsonBackReference
    private List<Event>  events = new ArrayList<>();
    public Volunteer(VolunteerModel volunteer){
        this.name=volunteer.getName();
        this.address=volunteer.getAddress();
        this.events=volunteer.getEvents();
        this.vid=volunteer.getVid();
        this.skills=volunteer.getSkills();
        this.messages=volunteer.getMessages();
        this.organisations=volunteer.getOrganisations();
    }
    public Volunteer(User user){
        this.name=user.getName();
        this.address=user.getAddress();
        this.skills=user.getSkills();
    }
}