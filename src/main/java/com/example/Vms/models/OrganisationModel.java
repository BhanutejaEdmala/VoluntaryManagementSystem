package com.example.Vms.models;

import com.example.Vms.entities.Event;
import com.example.Vms.entities.Organisation;
import com.example.Vms.entities.Volunteer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrganisationModel {
    private int oid;
    private String name;
    private String address;
    private String mobile;
    private Set<Volunteer> volunteers = new LinkedHashSet<>();
    private List<Event> events = new ArrayList<>();
    public OrganisationModel(Organisation organisation){
        this.oid=organisation.getOid();
        this.name=organisation.getName();
        this.address=organisation.getAddress();
        this.events=organisation.getEvents();
        this.mobile=organisation.getMobile();
        this.volunteers=organisation.getVolunteers();
    }
}
