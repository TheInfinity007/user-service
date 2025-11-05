package com.theinfinity007.user_service.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "User")
public class UserEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "gender")
    private String gender;

    @Column(name = "title")
    private String title;

    @Column(name = "birthday")
    private LocalDate birthday;
}
