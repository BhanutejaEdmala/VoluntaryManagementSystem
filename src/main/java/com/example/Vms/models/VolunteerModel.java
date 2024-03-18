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
    public class VolunteerModel {
        private int vid;
        private String name;
        private String address;
        private Set<String> messages = new LinkedHashSet<>();
        private Set<String> skills = new LinkedHashSet<>();
        private Set<Organisation> organisations = new LinkedHashSet<>();
        private List<Event> events = new ArrayList<>();
    }

