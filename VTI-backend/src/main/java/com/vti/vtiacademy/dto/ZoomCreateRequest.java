package com.vti.vtiacademy.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ZoomCreateRequest {

    @NotBlank(message = "{user_not_blank}")
    private String name;

    private String link;

    private String description;

    private String meetingId;

    private String passCode;
}
