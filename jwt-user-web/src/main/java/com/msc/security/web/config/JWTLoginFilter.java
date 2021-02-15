package com.msc.security.web.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JWTLoginFilter extends UsernamePasswordAuthenticationFilter {

    public JWTLoginFilter() {

        setFilterProcessesUrl("/login");
    }

    /* 시큐리티에 필터(SecurityConfig)를 설정해주면 로그인 시도시 attemptAuthentication 메소드로 들어온다. */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        return super.attemptAuthentication(request, response);
    }
}
