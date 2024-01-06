package com.vti.vtiacademy.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "class")
public class ClassEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "class_name", nullable = false, columnDefinition = "nvarchar(50)", unique = true)
    private String className;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ClassStatus status;

    @Column(name = "teaching_from")
    @Enumerated(EnumType.STRING)
    private TeachingForm teachingFrom;

    @ManyToOne
    @JoinColumn(name = "mentor_id")
    private Account mentor;

    @ManyToOne
    @JoinColumn(name = "zoom_id")
    private Zoom zoom;

    @ManyToOne
    @JoinColumn(name = "class_room_id")
    private ClassRoom classRoom;

    @Column(name = "description", nullable = false, columnDefinition = "nvarchar(500)")
    private String description;


}
