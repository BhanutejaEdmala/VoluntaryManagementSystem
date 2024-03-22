package com.example.Vms.entities;

import com.example.Vms.validation.MyValid;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.LinkedHashSet;
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
    @Column(unique = true)
    private String name;
    @Size(min = 3, message = "password size should be greater than 3 characters")
    @NotEmpty(message = "password shouldn't be empty")
    private String password;
    private String roles = "user";
    @NotEmpty(message = "address Shouldn't be empty")
    private String address;
    @MyValid
    private Set<String> skills = new LinkedHashSet<>();
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "user_organisation",
            joinColumns = @JoinColumn(name = "uid"),
            inverseJoinColumns = @JoinColumn(name = "oid"))
    @JsonIgnore
    private List<Organisation> organisations = new ArrayList<>();
    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Volunteer> volunteers = new ArrayList<>();
    @JsonIgnore
    private List<String> certificates = new ArrayList<>();
    @JsonIgnore
    private List<String> messages = new ArrayList<>();
}