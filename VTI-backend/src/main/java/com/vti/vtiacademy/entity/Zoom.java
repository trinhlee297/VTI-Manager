package com.vti.vtiacademy.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "zoom")
public class Zoom {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true, columnDefinition = "nvarchar(500)")
    private String name;

    @Column(name = "link", nullable = false, length = 200)
    private String link;

    @Column(name = "description", columnDefinition = "nvarchar(500)")
    private String description;

    @Column(name = "meeting_id", nullable = false, length = 50)
    private String meetingId;

    @Column(name = "pass_code", nullable = false, length = 50)
    private String passCode;


}
