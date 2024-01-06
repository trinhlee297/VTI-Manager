package com.vti.vtiacademy.dto;

import com.vti.vtiacademy.entity.Role;
import lombok.Data;


@Data

public class LoginDto {
    private Long id;
    private String username;
    private String fullName;
    private Role role;

    private String token;// co chua chu ky bi mat
}
