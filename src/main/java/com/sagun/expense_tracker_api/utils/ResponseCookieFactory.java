package com.sagun.expense_tracker_api.utils;

import jakarta.persistence.PreUpdate;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class ResponseCookieFactory {

    private static final String NAME = "refresh_token";
    private static final String PATH = "/api/v1/auth/refresh-token";

    private final boolean secure = true;
    private final Duration maxAge = Duration.ofDays(7);

    public ResponseCookie create(String refreshToken){
        return build(refreshToken,maxAge);
    }

    public ResponseCookie clear(){
        return build("",Duration.ZERO);
    }

    private final ResponseCookie build (String value, Duration age){
        return ResponseCookie.from(NAME,value)
                .httpOnly(true)
                .secure(secure)
                .maxAge(age)
                .sameSite("strict")
                .path(PATH)
                .build();
    }

}
