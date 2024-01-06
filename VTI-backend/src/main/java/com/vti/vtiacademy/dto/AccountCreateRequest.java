package com.vti.vtiacademy.dto;

import com.vti.vtiacademy.entity.Role;
import com.vti.vtiacademy.validation.CheckUserName;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Data
public class AccountCreateRequest {

    @NotBlank(message = "User Name can't blank")
    @Length(max = 50, message = "Max length of user name is 50")
//    @CheckUserName
//    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "User name không đúng định dạng")
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
