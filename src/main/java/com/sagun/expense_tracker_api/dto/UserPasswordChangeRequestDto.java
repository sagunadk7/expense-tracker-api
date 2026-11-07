package com.sagun.expense_tracker_api.dto;

public record UserPasswordChangeRequestDto(String currentPassword, String newPassword) {
}
