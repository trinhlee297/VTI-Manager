package com.vti.vtiacademy.dto;

import com.vti.vtiacademy.entity.Role;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AccountUpdateRequest {
    private Long id;
    private String username;
    private LocalDate dateOfBirth;
    private String address;
    private String password;
    private String fullName;
    private Role role;
    private String phoneNumber;
    private String email;
    private String facebook;
    private String information;
    private Long classEntityId;
}
