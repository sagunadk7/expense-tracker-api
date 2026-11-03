package com.sagun.expense_tracker_api.controller;

import com.sagun.expense_tracker_api.dto.UserRegistrationDto;
import com.sagun.expense_tracker_api.entity.User;
import com.sagun.expense_tracker_api.service.UserService;
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
    public User register(@RequestBody UserRegistrationDto dto){
        System.out.println("This ran ");
        return service.register(dto);
    }

}
