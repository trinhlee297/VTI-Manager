package com.vti.vtiacademy.form;

import com.vti.vtiacademy.dto.SearchBase;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ClassRoomFilterForm extends SearchBase {
    private String address;
    private String name;
    private Integer minSize;
    private Integer maxSize;
//
//    private LocalDate minCreatedDate;
//    private LocalDate maxCreatedDate;
}
