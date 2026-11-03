package com.sagun.expense_tracker_api.dto;

import com.sagun.expense_tracker_api.enums.Role;

import java.util.Set;

public record UserRegistrationDto (String username, String email, String password){
}
