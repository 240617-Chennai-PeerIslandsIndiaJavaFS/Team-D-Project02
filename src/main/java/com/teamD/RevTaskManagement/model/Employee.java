package com.teamD.RevTaskManagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.teamD.RevTaskManagement.enums.EmployeeStatus;
import com.teamD.RevTaskManagement.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long employeeId;

    private String employeeName;
    
    @Column(nullable = false, unique = true)
    private String email;
    private Date dateOfJoining;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String description;
    private String phone;

    @Enumerated(EnumType.STRING)
    private EmployeeStatus status;

    private String password;
    @JsonIgnore
    @ManyToMany(mappedBy = "team", cascade = CascadeType.ALL)
    private List<Project> projects;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "employee_skills",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    private List<Skill> skills;
}
