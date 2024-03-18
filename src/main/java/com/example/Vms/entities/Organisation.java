package com.example.Vms.entities;

import com.example.Vms.models.OrganisationModel;
import com.example.Vms.validation.MValid;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
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
    @NotEmpty
    private String name;
    @NotEmpty
    private String address;
    @NotEmpty
    @Pattern(regexp="\\d{10}", message="Mobile number must be 10 digits")
    @MValid
    @Column(unique = true)
    private String mobile;
    @ManyToMany(mappedBy = "organisations",cascade =  CascadeType.ALL,fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Volunteer> volunteers;
    @ManyToMany(mappedBy = "organisations",cascade =  { CascadeType.PERSIST, CascadeType.MERGE},fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Event> events= new ArrayList<>();
    @ManyToMany(mappedBy = "organisations",cascade =  { CascadeType.PERSIST, CascadeType.MERGE},fetch = FetchType.EAGER)
   @JsonIgnore
    private List<User> users = new ArrayList<>();
    @JsonIgnore
    private List<String> messages = new ArrayList<>();
    @JsonIgnore
    private List<Integer> closedevents = new ArrayList<>();
}