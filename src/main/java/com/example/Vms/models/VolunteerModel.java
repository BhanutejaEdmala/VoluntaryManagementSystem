package com.example.Vms.models;

import com.example.Vms.entities.Event;
import com.example.Vms.entities.Organisation;
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
    public class VolunteerModel {
        private String name;
        private String address;
        @JsonIgnore
        private Set<String> messages = new LinkedHashSet<>();
        private Set<String> skills = new LinkedHashSet<>();
        @JsonIgnore
        private Set<Organisation> organisations = new LinkedHashSet<>();
        @JsonIgnore
        private List<Event> events = new ArrayList<>();
    }

