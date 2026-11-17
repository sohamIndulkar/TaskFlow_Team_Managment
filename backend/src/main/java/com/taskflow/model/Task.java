package com.taskflow.model;

import jakarta.persistence.*;
import java.time.*;

@Entity @Table(name="tasks", indexes={@Index(name="idx_task_project_status",columnList="project_id,status"),@Index(name="idx_task_assignee",columnList="assignee_id")})
public class Task {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) public Long id;
    @Column(nullable=false) public String title;
    @Column(length=2000) public String description;
    @Enumerated(EnumType.STRING) @Column(nullable=false) public TaskStatus status=TaskStatus.TODO;
    @Enumerated(EnumType.STRING) @Column(nullable=false) public Priority priority=Priority.MEDIUM;
    @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="project_id",nullable=false) public Project project;
    @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="assignee_id") public AppUser assignee;
    @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="creator_id",nullable=false) public AppUser creator;
    public LocalDate dueDate;
    @Column(nullable=false) public Instant createdAt=Instant.now();
    @Column(nullable=false) public Instant updatedAt=Instant.now();
    @Version public long version;
    protected Task(){}
    public Task(String title,String description,Priority priority,Project project,AppUser assignee,AppUser creator,LocalDate dueDate){this.title=title;this.description=description;this.priority=priority;this.project=project;this.assignee=assignee;this.creator=creator;this.dueDate=dueDate;}
    @PreUpdate void touch(){updatedAt=Instant.now();}
}
