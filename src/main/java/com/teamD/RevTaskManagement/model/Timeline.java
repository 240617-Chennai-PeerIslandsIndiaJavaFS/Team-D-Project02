package com.teamD.RevTaskManagement.model;

import com.teamD.RevTaskManagement.enums.Milestone;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Timeline {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long timelineId;

    @Enumerated(EnumType.STRING)
    private Milestone milestone;

    private Date timestamp;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "task_id")
    private Task task;

//    @OneToOne(cascade = CascadeType.ALL)
    private String employeeName;
}
