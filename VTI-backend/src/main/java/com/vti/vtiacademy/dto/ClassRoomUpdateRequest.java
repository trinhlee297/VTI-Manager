package com.vti.vtiacademy.dto;

import lombok.Data;

@Data
public class ClassRoomUpdateRequest {
    private Long id;
    private String name;
    private String address;
    private String note;
    private Integer size;
}
