package com.vti.vtiacademy.dto;

import com.vti.vtiacademy.exepction.ErrorResponseEnum;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CustomExceptionDto {
    private LocalDateTime timestamp;
    private int status;
    private String message;
    private String path;

    public CustomExceptionDto(ErrorResponseEnum errorResponseEnum){
        this.message = errorResponseEnum.message;
        this.status = errorResponseEnum.status;
        this.timestamp = LocalDateTime.now();
    }

    public CustomExceptionDto(String message, int status, String path){
        this.status = status;
        this.message = message;
        this.path = path;
    }
}
