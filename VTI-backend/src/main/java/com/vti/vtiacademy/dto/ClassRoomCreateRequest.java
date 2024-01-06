package com.vti.vtiacademy.dto;

import lombok.Data;

@Data
public class ClassRoomCreateRequest {
    private String name;
    private String address;
    private String note;
    private Integer size;

}
