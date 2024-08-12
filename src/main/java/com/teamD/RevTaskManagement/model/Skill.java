package com.teamD.RevTaskManagement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long skillId;

    private String skill;
    private String description;

    @ManyToMany(mappedBy = "skills", cascade = CascadeType.ALL)
    private List<Employee> employees;
}
