package com.sagun.expense_tracker_api.dto;

import org.springframework.http.HttpStatus;

public record UserPasswordChangeResponseDto(
        boolean success,
        HttpStatus httpStatus,
        String message

) {
}
