package com.taskflow.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity @Table(name="task_comments")
public class TaskComment {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) public Long id;
    @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="task_id",nullable=false) public Task task;
    @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="author_id",nullable=false) public AppUser author;
    @Column(nullable=false,length=2000) public String body;
    @Column(nullable=false) public Instant createdAt=Instant.now();
    protected TaskComment(){}
    public TaskComment(Task task,AppUser author,String body){this.task=task;this.author=author;this.body=body;}
}
