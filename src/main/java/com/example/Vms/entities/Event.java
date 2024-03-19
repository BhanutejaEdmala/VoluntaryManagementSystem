package com.example.Vms.entities;
import com.example.Vms.models.EventModel;
import com.example.Vms.validation.Date;
import com.example.Vms.validation.MyValid;
import com.example.Vms.validation.Timings;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.ArrayList;
import java.util.LinkedHashSet;
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
    @NotEmpty
    private String name;
    @NotEmpty
    private String location;
    @NotEmpty
    @Date
    private String date;
    @MyValid
    private Set<String> skills_good_to_have = new LinkedHashSet<>();
    private String status="active";
    @Timings
    private String timings;
    @JsonIgnore
    @ManyToMany(cascade =  { CascadeType.PERSIST, CascadeType.MERGE },fetch = FetchType.EAGER)
    @JoinTable(
            name = "organisation_event",
            joinColumns = @JoinColumn(name = "eid"),
            inverseJoinColumns = @JoinColumn(name = "oid")
    )
    private List<Organisation> organisations = new ArrayList<>();
    @JsonIgnore
    @ManyToMany(mappedBy = "events",cascade = {CascadeType.PERSIST,CascadeType.MERGE},fetch = FetchType.EAGER)
    private List<Volunteer> volunteerList = new ArrayList<>();

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

}