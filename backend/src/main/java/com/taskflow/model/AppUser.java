package com.taskflow.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity @Table(name="users")
public class AppUser {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) public Long id;
    @Column(nullable=false) public String name;
    @Column(nullable=false, unique=true) public String email;
    @Column(nullable=false) public String passwordHash;
    @Enumerated(EnumType.STRING) @Column(nullable=false) public Role role = Role.MEMBER;
    public String avatar;
    @Column(nullable=false) public Instant createdAt = Instant.now();
    protected AppUser() {}
    public AppUser(String name,String email,String passwordHash,Role role){this.name=name;this.email=email;this.passwordHash=passwordHash;this.role=role;}
}
