package com.vti.vtiacademy.validation;

import com.vti.vtiacademy.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CheckUserNameValidator implements ConstraintValidator<CheckUserName, String> {
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        // Logic kiểm tra dữ liệu
        return !accountRepository.existsByUsername(username); //true: thoả mãn, false: ko thoả mãn
    }
}
