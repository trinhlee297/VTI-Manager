package com.vti.vtiacademy.utils;

import com.alibaba.fastjson.JSON;
import com.vti.vtiacademy.dto.CustomExceptionDto;
import com.vti.vtiacademy.dto.LoginDto;
import com.vti.vtiacademy.entity.Role;
import com.vti.vtiacademy.exepction.CustomException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Slf4j
@Component
public class JWTTokenUtils {
    private static final long EXPIRATION_TIME = 30000000; // 10 days, thời hạn của token
    private static final String SECRET = "123456"; // Chữ ký bí mật
    private static final String PREFIX_TOKEN = "Bearer"; // Ký tự đầu của token
    private static final String AUTHORIZATION = "Authorization"; // Key của token trên header


    // Hàm này dùng để tạo ra token
    public String createAccessToken(LoginDto loginDto) {
        // Tạo giá trị thời hạn token ( bằng thời gian hiện tại + 10 ngày hoặc tuỳ theo )
        Date expirationDate = new Date(System.currentTimeMillis() + EXPIRATION_TIME);
        String token = Jwts.builder()
                .setId(String.valueOf(loginDto.getId())) //set giá trị Id
                .setSubject(loginDto.getUsername()) // set giá trị subject
                .setIssuedAt(new Date())
                .setIssuer("VTI")
                .setExpiration(expirationDate) // set thời hạn của token
                .signWith(SignatureAlgorithm.HS256, SECRET) // khai báo phương thức mã hóa token và chữ ký bí mật
                .claim("authorities", loginDto.getRole().name()).compact();// thêm trường user-Agent để lưu thông tin trình duyệt đang dùng
        return token;
    }


    // Hàm này dùng để giải mã hóa token
    public LoginDto parseAccessToken(String token) {
        LoginDto loginDto = new LoginDto();
        if (!token.isEmpty()) {
            try {
                token = token.replace(PREFIX_TOKEN, "").trim();
                Claims claims = Jwts.parser()
                        .setSigningKey(SECRET)
                        .parseClaimsJws(token).getBody();
                // Lấy ra các thông tin
                String user = claims.getSubject();
                Role role = Role.valueOf(claims.get("authorities").toString());
                // Gán các thông tin vào đối tượng LoginDto, có thể sử dụng constructor
                loginDto.setUsername(user);
                loginDto.setRole(role);
            } catch (Exception e) {
                log.error(e.getMessage());
                return null;
            }
        }
        return loginDto;
    }


    public LoginDto checkToken(String token, HttpServletResponse response, HttpServletRequest httpServletRequest) {
        LoginDto loginDto = new LoginDto();
        try {
            if (StringUtils.isBlank(token) || !token.startsWith(PREFIX_TOKEN)) { // token bị trống -> lỗi
                responseJson(response, new CustomExceptionDto("Token ko hợp lệ", 401, httpServletRequest.getRequestURI()));
                return null;
            }
            token = token.replace(PREFIX_TOKEN, "").trim();
            loginDto = parseAccessToken(token);
            if (loginDto == null) { // Ko có token trên hệ thống
                responseJson(response, new CustomExceptionDto("Token ko tồn tại hoặc hết hạn", 401, httpServletRequest.getRequestURI()));
                return null;
            }
        } catch (Exception e) {
            responseJson(response, new CustomExceptionDto(e.getMessage(), 401, httpServletRequest.getRequestURI()));
            return null;
        }
        return loginDto;
    }


    // Hàm này dùng để response dữ liệu khi gặp lỗi
    private void responseJson(HttpServletResponse response, CustomExceptionDto appException) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setStatus(appException.getStatus());
        try {
            response.getWriter().print(JSON.toJSONString(appException));
        } catch (IOException e) {
            log.debug(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}


