package com.sagun.expense_tracker_api.service;

import com.sagun.expense_tracker_api.dto.UserRegistrationRequestDto;
import com.sagun.expense_tracker_api.dto.UserRegistrationResponseDto;
import com.sagun.expense_tracker_api.entity.User;
import com.sagun.expense_tracker_api.repository.UserRepository;
import com.sagun.expense_tracker_api.utils.EmailAndPasswordValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder encoder;
    public UserRegistrationResponseDto register(UserRegistrationRequestDto request){
        if(repository.existsByUsername(request.username())){
            throw new IllegalArgumentException("Username already exists");
        }
        if(repository.existsByEmail(request.email())){
            throw new IllegalArgumentException("Email already exists");
        }
        if(!EmailAndPasswordValidator.isStrongPassword(request.password()) || !EmailAndPasswordValidator.isValidEmail(request.email())){
            throw new IllegalArgumentException("Invalid Password or Invalid Email");
        }
        User user = new User();
        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setPasswordHash(encoder.encode(request.password()));
        return UserRegistrationResponseDto.from(repository.saveAndFlush(user));
    }

}
