package com.sagun.expense_tracker_api.service;

import com.sagun.expense_tracker_api.dto.UserRegistrationDto;
import com.sagun.expense_tracker_api.entity.User;
import com.sagun.expense_tracker_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository repository;

    public void register(UserRegistrationDto request){
        User user = new User();
        user.setFullName(request.username());
        user.setEmail(request.email());
        user.setPasswordHash(request.password());
        user.setRoles(request.roles());
        user.setEnabled(true);
        repository.save(user);
    }

}
