package com.eventure.backend.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "site_user")
public class User {
    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    private Long id;
    private String username;
    private String email;
    private String picture;
    private LocalDate createdAt;

    public User() {}
    public User(String username, String email, LocalDate createdAt) {
        this.username = username;
        this.email = email;
        this.createdAt = createdAt;
        this.picture = "";
    }
    public User(String username, String email, String picture, LocalDate createdAt) {
        this.username = username;
        this.email = email;
        this.picture = picture;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public String getPicture() { return picture; }

    public void setPicture(String picture) { this.picture = picture; }
}
