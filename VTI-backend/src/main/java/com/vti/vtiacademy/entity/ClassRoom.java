package com.vti.vtiacademy.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "CLASS_ROOM")
public class ClassRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false, unique = true, columnDefinition = "nvarchar(200)")
    private String name;

    @Column(name = "address", nullable = false, columnDefinition = "nvarchar(200)")
    private String address;

    @Column(name = "note", columnDefinition = "nvarchar(500)")
    private String note;

    @Column(name = "size")
    private Integer size;

    // neu de int thi mac dinh se la 0
    // con neu de Integer thi mac dinh se null
}
