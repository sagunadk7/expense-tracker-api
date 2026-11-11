package com.sagun.expense_tracker_api.controller;

import com.sagun.expense_tracker_api.dto.*;
import com.sagun.expense_tracker_api.principal.UserPrincipal;
import com.sagun.expense_tracker_api.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/change-password")
    public UserPasswordChangeResponseDto changePassword(@AuthenticationPrincipal UserPrincipal principal, @RequestBody UserPasswordChangeRequestDto requestDto){
        return service.changePassword(requestDto,principal);
    }

    @PostMapping("/income")
    public ResponseEntity<String> setIncome(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody Long income){
        return service.setIncome(income,userPrincipal);
    }

    @PostMapping("/deactivate")
    public ResponseEntity<String> deactivate(@AuthenticationPrincipal UserPrincipal principal){
        return service.deactivate(principal);
    }

}
