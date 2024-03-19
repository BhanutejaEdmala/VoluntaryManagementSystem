package com.example.Vms.models;
import com.example.Vms.entities.Event;
import com.example.Vms.entities.Organisation;
import com.example.Vms.entities.Volunteer;
import com.example.Vms.validation.MyValid;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private String name;
    private String location;
    private String date;
    private String status="active";
    private Set<String> skills_good_to_have = new LinkedHashSet<>();
    private String timings;
    @JsonIgnore
    private List<Organisation> organisations = new ArrayList<>();
    @JsonIgnore
    private List<Volunteer> volunteerList = new ArrayList<>();
    @Override
    public String toString() {
        return "EventModel{" +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", date='" + date + '\'' +
                ", skills_good_to_have=" + skills_good_to_have +
                '}';
    }

}

