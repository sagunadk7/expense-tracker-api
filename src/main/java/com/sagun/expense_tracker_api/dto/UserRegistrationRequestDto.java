package com.sagun.expense_tracker_api.dto;

import jakarta.validation.constraints.*;

public record UserRegistrationRequestDto(
        @NotBlank
        @Size(min = 5,max = 50)
        @Pattern(regexp = "^[a-zA-Z0-9_]+$",message = "Only letters, numbers and underscores are allowed")
        String username,

        @NotBlank
        @Size(max = 255)
        @Email
        String email,

        @Size(min=8,max=128)
        String password){
}
