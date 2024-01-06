package com.vti.vtiacademy.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

//@Data
@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_name", nullable = false, unique = true, length = 50)
    private String username;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "address",columnDefinition = "nvarchar(500)")
    private String address;

    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @Column(name = "full_name",nullable = false, columnDefinition = "nvarchar(200)")
    private String fullName;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "phone_number",nullable = false,unique = true, columnDefinition = "varchar(12)")
    private String phoneNumber;


    @Column(name = "email", nullable = false,unique = true)
    private String email;


    @Column(name = "facebook", nullable = false, unique = true)
    private String facebook;


    @Column(name = "information", columnDefinition = "nvarchar(255)")
    private String information;

    @ManyToOne
    @JoinColumn(name = "class_entity_id")
    private ClassEntity classEntity;

}


