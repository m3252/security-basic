package com.msc.security.web.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.msc.security.web.dto.UserLogin;
import com.msc.security.web.util.JWTUtil;
import lombok.SneakyThrows;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JWTLoginFilter extends UsernamePasswordAuthenticationFilter {

    private final JWTUtil jwtUtil;
    private ObjectMapper objectMapper;

    public JWTLoginFilter(JWTUtil jwtUtil, ObjectMapper objectMapper) {
        this.jwtUtil = jwtUtil;
        this.objectMapper = objectMapper;
        setFilterProcessesUrl("/login");
    }

    /* 시큐리티에 필터(SecurityConfig)를 설정해주면 로그인 시도시 attemptAuthentication 메소드로 들어온다. */
    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        objectMapper.readValue(request.getInputStream(), UserLogin.class);
        return super.attemptAuthentication(request, response);
    }
}
