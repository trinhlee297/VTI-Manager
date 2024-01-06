package com.vti.vtiacademy.dto;

import com.vti.vtiacademy.entity.Role;
import lombok.Data;

import java.time.LocalDate;

@Data
public class RegisterAccount {
    private String username;
    private LocalDate dateOfBirth;
    private String address;
    private String password;
    private String fullName;
    private String phoneNumber;
    private String email;
    private String facebook;
}
