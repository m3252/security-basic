package com.msc.security.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.msc.security.web.dto.UserLogin;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class JWTLoginFilterTest {

    @LocalServerPort
    private int port;

    private RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private ObjectMapper objectMapper;

    private URI uri(String path) throws URISyntaxException {
        return new URI(format("http://localhost:%d%s", port, path));
    }

    @DisplayName("1. jwt 로 로그이을 시도한다.")
    @Test
    void test_1() throws URISyntaxException {
        UserLogin userLogin = UserLogin.builder()
                .username("user1@test.com")
                .password("1234")
                .build();

        HttpEntity<UserLogin> body = new HttpEntity<>(userLogin);
        ResponseEntity<String> res = restTemplate.exchange(uri("/login"), HttpMethod.POST, body, String.class);

        assertEquals(200, res.getStatusCodeValue());
        System.out.println(res.getHeaders().get("Authentication"));

    }

}
