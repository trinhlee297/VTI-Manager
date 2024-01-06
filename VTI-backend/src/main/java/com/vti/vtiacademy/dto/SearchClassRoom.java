package com.vti.vtiacademy.dto;

import lombok.Data;

@Data
public class SearchClassRoom {
    private String name;
    private Integer size;
    private Integer minSize;
    private Integer maxSize;
    private int pageNumber;
    private int pageSize;

}
