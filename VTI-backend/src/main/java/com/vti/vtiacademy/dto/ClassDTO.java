package com.vti.vtiacademy.dto;

import com.vti.vtiacademy.entity.*;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;



@Data
public class ClassDTO {
    private Long id;
    private String className;
    private LocalDate startDate;
    private LocalDate endDate;
    private ClassStatus status;
    private TeachingForm teachingFrom;
    private String mentor;
    private Zoom zoom;
    private ClassRoom classRoom;
    private String description;

}
