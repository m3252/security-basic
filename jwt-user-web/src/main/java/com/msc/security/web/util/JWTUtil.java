package com.msc.security.web.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

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


}
