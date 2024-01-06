package com.vti.vtiacademy.configuration;


import com.vti.vtiacademy.dto.LoginDto;
import com.vti.vtiacademy.utils.JWTTokenUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    private static final String AUTHORIZATION = "Authorization";


    @Autowired
    private JWTTokenUtils jwtTokenUtils;


    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, @NotNull HttpServletResponse httpServletResponse,
                                    @NotNull FilterChain filterChain) throws ServletException, IOException {
        // Lấy token từ api (request)
        String token = httpServletRequest.getHeader(AUTHORIZATION);
        String request = httpServletRequest.getRequestURI();
        if (StringUtils.containsAnyIgnoreCase(request, "/api/v1/auth")
                || StringUtils.containsAnyIgnoreCase(request, "/api/v1/account/create")
                || StringUtils.containsAnyIgnoreCase(request, "/api/v1/product/search")
                || StringUtils.containsAnyIgnoreCase(request, "/swagger-ui")
                || StringUtils.containsAnyIgnoreCase(request, "/swagger-resources")
                || StringUtils.containsAnyIgnoreCase(request, "/v3/api-docs")) {
            // Những api public ko cần check token -> doFilter
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        } else {
            // Kiểm tra token & Giải mã token -> lấy thông tin user -> authen
            LoginDto loginDto = jwtTokenUtils.checkToken(token, httpServletResponse, httpServletRequest);
            // Lấy danh sách quyền của user
            if (loginDto != null) {
                List<GrantedAuthority> authorities = new ArrayList<>();
                authorities.add(loginDto.getRole());
                // Tạo đối tượng để Authen vào hệ thống
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        loginDto.getUsername(), null, authorities);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                filterChain.doFilter(httpServletRequest, httpServletResponse);
            }
        }
    }
}


