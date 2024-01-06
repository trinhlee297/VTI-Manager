package com.vti.vtiacademy.form;

import com.vti.vtiacademy.dto.SearchBase;
import com.vti.vtiacademy.entity.ClassStatus;
import com.vti.vtiacademy.entity.TeachingForm;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ClassEntityFilterForm extends SearchBase {
    private String className;
    private LocalDate startDateMin;
    private LocalDate endDateMin;
    private ClassStatus classStatus;
    private TeachingForm teachingForm;
    private Long zoomId;
    private Long classRoomId;
}
