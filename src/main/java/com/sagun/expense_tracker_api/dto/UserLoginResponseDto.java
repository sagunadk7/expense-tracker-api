package com.sagun.expense_tracker_api.dto;

import com.sagun.expense_tracker_api.entity.User;
import com.sagun.expense_tracker_api.enums.Role;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Set;

public record UserLoginResponseDto(
        boolean success,
        String message,
        Data data,
        String timestamp
) {
    public record Data(
            String accessToken,
            String tokenType,
            Users user,
            long expiresIn
    ){}
    public record Users(long id, String username, String email, Set<Role> roles){}

    public static UserLoginResponseDto from(User user,String accessToken, String tokenType, long expiresIn){
        Users users = new Users(user.getId(), user.getUsername(), user.getEmail(), user.getRoles());
        Data data1 = new Data(accessToken, tokenType, users, expiresIn);
        return new UserLoginResponseDto(true,"Login Successful",data1,Instant.now().truncatedTo(ChronoUnit.SECONDS).toString());
    }
}
