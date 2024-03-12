package com.example.Vms.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
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
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int uid;
    @NotEmpty(message = "name shouldn't be empty")
    private String name;
    @Size(min = 3,max = 8,message ="password size should be in between 3 and 8 characters")
    @NotEmpty(message = "password shouldn't be empty")
    private String password;
    private String roles="user";
    @NotEmpty(message = "address Shouldn't be empty")
    private String address;
    private Set<String> skills;
    @ManyToMany
    @JoinTable(name = "user_organisation",
            joinColumns = @JoinColumn(name = "uid"),
            inverseJoinColumns = @JoinColumn(name = "oid"))
    private List<Organisation> organisations = new ArrayList<>();
    @OneToMany(mappedBy = "user",cascade =CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Volunteer>  volunteers = new ArrayList<>();
}