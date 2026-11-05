package com.sagun.expense_tracker_api.dto;

import jakarta.validation.constraints.Email;

public record UserLoginRequestDto(
        @Email(message = "Invalid Email")
        String email,
        String password){
}
