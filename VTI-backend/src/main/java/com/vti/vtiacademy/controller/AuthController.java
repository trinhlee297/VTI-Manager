package com.vti.vtiacademy.controller;


import com.vti.vtiacademy.dto.LoginDto;
import com.vti.vtiacademy.dto.LoginRequest;
import com.vti.vtiacademy.dto.RegisterAccount;
import com.vti.vtiacademy.entity.Account;
import com.vti.vtiacademy.entity.Role;
import com.vti.vtiacademy.entity.Status;
import com.vti.vtiacademy.exepction.CustomException;
import com.vti.vtiacademy.exepction.ErrorResponseEnum;
import com.vti.vtiacademy.repository.AccountRepository;
import com.vti.vtiacademy.service.impl.MailSenderService;
import com.vti.vtiacademy.utils.JWTTokenUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin("*")
@Validated
public class AuthController {
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    JWTTokenUtils jwtTokenUtils;

    @Autowired
    MailSenderService mailSenderService;

    @PostMapping("/login-v1")
    public LoginDto login(@RequestParam String userName, @RequestParam String password){
        Optional<Account> accountOptional = accountRepository.findByUsername(userName);
        if (accountOptional.isEmpty()){
            throw new CustomException(ErrorResponseEnum.USERNAME_NOT_FOUND);
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Account account = accountOptional.get();
        boolean checkPassword = passwordEncoder.matches(password, account.getPassword());
        if (!checkPassword){
            throw new CustomException(ErrorResponseEnum.PASSWORD_FAILS);
        }
        LoginDto loginDto = new LoginDto();
        BeanUtils.copyProperties(account, loginDto);
        // so sánh password

        return loginDto;
    };


    @PostMapping("/login-jwt")
    public LoginDto loginJwt(@RequestBody LoginRequest request){
        Optional<Account> accountOptional = accountRepository.findByUsername(request.getUserName());
        if (accountOptional.isEmpty()){
            throw new CustomException(ErrorResponseEnum.USERNAME_NOT_FOUND);
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Account account = accountOptional.get();
        boolean checkPassword = passwordEncoder.matches(request.getPassword(), account.getPassword());
        if (!checkPassword){
            throw new CustomException(ErrorResponseEnum.PASSWORD_FAILS);
        }
        // check trang thai account
        if (account.getStatus() != Status.ACTIVE){
            throw new CustomException(ErrorResponseEnum.ACC_NOT_ACTIVE);
        }
        LoginDto loginDto = new LoginDto();
        BeanUtils.copyProperties(account, loginDto);
        String token = jwtTokenUtils.createAccessToken(loginDto);
        loginDto.setToken(token);
        return loginDto;
    };

    @GetMapping("/active/{id}")
    public String active(@PathVariable Long id){
        Optional<Account> accountOptional = accountRepository.findById(id);
        if (accountOptional.isEmpty()){
           return "Người dùng không tồn tại";
        }
        Account account = accountOptional.get();
        account.setStatus(Status.ACTIVE);
        accountRepository.save(account);
        return "Kích hoạt thành công";

    }


    @PostMapping("/register")
    public void registerAccount(@RequestBody RegisterAccount request) throws MessagingException {
       Account account = new Account();
       BeanUtils.copyProperties(request, account);
       account.setStatus(Status.PENDING);
       account.setRole(Role.STUDENT);
       String encodePass = new BCryptPasswordEncoder().encode(request.getPassword());
       account.setPassword(encodePass);

       account = accountRepository.save(account);

        String toMail = account.getEmail();
        String subject = "KÍCH HOẠT TÀI KHOẢN";
        String apiActive = "http://localhost:8888/api/v1/auth/active/" + account.getId();
        String text = "<div>\n" +
                "<h1>Bạn vừa đăng kí tài khoản, kích vào đường dẫn để kích hoạt tài khoản !</h1>\n" +
                "<h3>\n" +
                "<a href=\"" + apiActive +"\" target=\"_blank\">Kích hoạt</a>\n" +
                "</h3>\n" +
                "<img src=\"https://vtiacademy.edu.vn/upload/images/logo/academy-02-01-01-01.png\" alt=\"Girl in a jacket\" height=\"300\"\n" +
                "</div>";

        mailSenderService.sendMessageWithAttachment(toMail, subject, text);
    }

}
