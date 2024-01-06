package com.vti.vtiacademy.dto;

import com.vti.vtiacademy.entity.ClassStatus;
import com.vti.vtiacademy.entity.TeachingForm;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ClassEntityCreateRequest {
    private String className;
    private LocalDate startDate;
    private LocalDate endDate;
    private ClassStatus status;
    private TeachingForm teachingFrom;
    private String description;

    private Long accountId;
    private Long zoomId;
    private Long classRoomId;
}
