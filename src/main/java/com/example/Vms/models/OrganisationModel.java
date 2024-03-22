package com.example.Vms.models;

import com.example.Vms.entities.Event;
import com.example.Vms.entities.Organisation;
import com.example.Vms.entities.Volunteer;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private String name;
    private String address;
    private String mobile;
    @JsonIgnore
    private Set<Volunteer> volunteers = new LinkedHashSet<>();
    @JsonIgnore
    private List<Event> events = new ArrayList<>();
    @JsonIgnore
    private List<String> adminDetails = new ArrayList<>();
    @JsonIgnore
    private List<Integer> waitingListUserIds = new ArrayList<>();
}
