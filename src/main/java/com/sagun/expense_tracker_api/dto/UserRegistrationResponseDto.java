package com.sagun.expense_tracker_api.dto;

import com.sagun.expense_tracker_api.entity.User;
import com.sagun.expense_tracker_api.enums.Role;

import java.time.Instant;
import java.util.Set;

public record UserRegistrationResponseDto(
        boolean success,
        String message,
        Data data,
        Instant timestamp
) {
    public record Data( UserRecord user){
    }
    public record UserRecord(Long id, String username, String email, Set<Role> roles, boolean enabled, Instant createdAt){}

    public static UserRegistrationResponseDto from(User user){
        UserRecord userRecord = new UserRecord(user.getId(), user.getUsername(), user.getEmail(), user.getRoles(), user.isEnabled(), user.getCreatedAt());
        Data data1 = new Data(userRecord);
        return new UserRegistrationResponseDto(true,"User registered successfully",data1,Instant.now());
    }
}
