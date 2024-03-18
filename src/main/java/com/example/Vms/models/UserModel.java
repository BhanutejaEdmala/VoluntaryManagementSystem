package com.example.Vms.models;

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
public class UserModel {
    private String name;
    private String password;
    private String roles="user";
    private String address;
    private Set<String> skills=  new LinkedHashSet<>();
    @JsonIgnore
    private List<Organisation> organisations=new ArrayList<>();
    @JsonIgnore
    private List<Volunteer> volunteers = new ArrayList<>();
    @JsonIgnore
    private List<String> certificates = new ArrayList<>();
}
