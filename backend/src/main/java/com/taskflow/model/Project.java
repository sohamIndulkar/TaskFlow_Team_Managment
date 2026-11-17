package com.taskflow.model;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.*;

@Entity @Table(name="projects")
public class Project {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) public Long id;
    @Column(nullable=false) public String name;
    @Column(length=1200) public String description;
    @Column(nullable=false) public String color="#4f46e5";
    @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="owner_id",nullable=false) public AppUser owner;
    @ManyToMany(fetch=FetchType.LAZY) @JoinTable(name="project_members",joinColumns=@JoinColumn(name="project_id"),inverseJoinColumns=@JoinColumn(name="user_id")) public Set<AppUser> members=new HashSet<>();
    @Column(nullable=false) public Instant createdAt=Instant.now();
    protected Project(){}
    public Project(String name,String description,String color,AppUser owner){this.name=name;this.description=description;this.color=color;this.owner=owner;this.members.add(owner);}
}
