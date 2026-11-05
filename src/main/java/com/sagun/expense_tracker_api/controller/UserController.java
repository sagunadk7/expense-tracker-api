package com.sagun.expense_tracker_api.controller;

import com.sagun.expense_tracker_api.dto.UserLoginRequestDto;
import com.sagun.expense_tracker_api.dto.UserLoginResponseDto;
import com.sagun.expense_tracker_api.dto.UserRegistrationRequestDto;
import com.sagun.expense_tracker_api.dto.UserRegistrationResponseDto;
import com.sagun.expense_tracker_api.entity.User;
import com.sagun.expense_tracker_api.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class UserController {

    private final UserService service;

    @PostMapping("/register")
    public UserRegistrationResponseDto register(@Valid  @RequestBody UserRegistrationRequestDto dto){
        return service.register(dto);
    }

    @PostMapping("/login")
    public UserLoginResponseDto login(@Valid @RequestBody UserLoginRequestDto dto, HttpServletResponse response){
        return service.login(dto, response);
    }

}
