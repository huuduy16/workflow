package com.wfl.domains.entities;

import java.time.LocalDate;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "t_task")
public class Task {

    @Id
    private String id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "created_user_id")
    private Long createdUserId;
    @Column(name = "created_time")
    private Date createdTime;
    @Column(name = "status")
    private String status;
    @Column(name = "assignee_id")
    private Long assigneeId;
    @Column(name = "category_id")
    private Long categoryId;
    @Column(name = "start_date")
    private LocalDate startDate;
    @Column(name = "due_date")
    private LocalDate dueDate;
    @Column(name = "estimated_hours")
    private Integer estimatedHours;
    @Column(name = "actual_hours")
    private Integer actualHours;
}
