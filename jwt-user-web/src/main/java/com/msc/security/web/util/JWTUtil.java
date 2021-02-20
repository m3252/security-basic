package com.msc.security.web.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.msc.security.web.dto.VerifyResult;

import java.time.Instant;

public class JWTUtil {

    private String secret = "msc";
    private Algorithm AL = Algorithm.HMAC512(secret);
    private long lifeTime = 30;

    public String generate(String userId) {
        return JWT.create().withSubject(userId)
                .withClaim("exp", Instant.now().getEpochSecond() + lifeTime)
                .sign(AL);
    }

    public VerifyResult verify(String token) {
        try {
            DecodedJWT decodedJWT = JWT.require(AL).build().verify(token);
            return VerifyResult.builder()
                    .userId(decodedJWT.getSubject())
                    .result(true)
                    .build();
        } catch (JWTVerificationException ex) {
            DecodedJWT decodedJWT = JWT.decode(token);
            return VerifyResult.builder()
                    .userId(decodedJWT.getSubject())
                    .result(false)
                    .build();
        }

    }
}
