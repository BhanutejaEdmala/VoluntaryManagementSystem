package com.example.Vms.entities;

import com.example.Vms.models.OrganisationModel;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Organisation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int oid;
    private String name;
    private String address;
    private String mobile;
    @ManyToMany(mappedBy = "organisations",cascade =  { CascadeType.PERSIST, CascadeType.MERGE,CascadeType.REMOVE},fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Volunteer> volunteers;
    @ManyToMany(mappedBy = "organisations",cascade =  { CascadeType.PERSIST, CascadeType.MERGE,CascadeType.REMOVE},fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Event> events;
    @ManyToMany(mappedBy = "organisations",cascade =  { CascadeType.PERSIST, CascadeType.MERGE},fetch = FetchType.EAGER)
   @JsonIgnore
    private List<User> users = new ArrayList<>();
    public Organisation(OrganisationModel organisation){
        this.oid=organisation.getOid();
        this.name=organisation.getName();
        this.address=organisation.getAddress();
        this.events=organisation.getEvents();
        this.mobile=organisation.getMobile();
        this.volunteers=organisation.getVolunteers();
    }
}