package com.sagun.expense_tracker_api.service;

import com.sagun.expense_tracker_api.customExceptions.UserNotFoundException;
import com.sagun.expense_tracker_api.dto.*;
import com.sagun.expense_tracker_api.entity.User;
import com.sagun.expense_tracker_api.principal.UserPrincipal;
import com.sagun.expense_tracker_api.repository.UserRepository;
import com.sagun.expense_tracker_api.utils.EmailAndPasswordValidator;
import com.sagun.expense_tracker_api.utils.JWTUtils;
import com.sagun.expense_tracker_api.utils.ResponseCookieFactory;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Locale;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder encoder;
    private final ResponseCookieFactory cookieFactory;
    private final JWTUtils jwtUtils;

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
        user.setUsername(request.username().toLowerCase(Locale.ROOT));
        user.setEmail(request.email().toLowerCase(Locale.ROOT));
        user.setPasswordHash(encoder.encode(request.password()));
        return UserRegistrationResponseDto.from(repository.saveAndFlush(user));
    }

    public UserLoginResponseDto login(UserLoginRequestDto requestDto, HttpServletResponse response){
        String email = requestDto.email().toLowerCase(Locale.ROOT);
        String password = requestDto.password();
        if(!EmailAndPasswordValidator.isValidEmail(email) || !repository.existsByEmail(email)){
            throw new IllegalArgumentException("Invalid Email or Password");
        }
        User user = repository.findByEmail(email).orElseThrow(()->new UserNotFoundException("User does not exist"));
        if(!encoder.matches(password,user.getPasswordHash())){
            throw new IllegalArgumentException("Invalid Email or Password");
        }
        if(!user.isEnabled()) throw new DisabledException("Account is Disabled");

        String accessToken = jwtUtils.generateAccessToken(user.getEmail());
        String refreshToken = jwtUtils.generateRefreshToken(user.getEmail());
        response.addHeader(HttpHeaders.SET_COOKIE, cookieFactory.create(refreshToken).toString());
        return UserLoginResponseDto.from(user,accessToken,"Bearer", Duration.ofMinutes(15).toSeconds());
    }

    public UserPasswordChangeResponseDto changePassword(UserPasswordChangeRequestDto request, UserPrincipal principal){
        User user = repository.findByEmail(principal.getEmail()).orElseThrow(() -> new UserNotFoundException("User does not exists"));
        if(!user.isEnabled()) throw new DisabledException("Account is disabled");
        if(!encoder.matches(request.currentPassword(),user.getPasswordHash())){
            return new UserPasswordChangeResponseDto(false, HttpStatus.UNAUTHORIZED,"Invalid current password or expired authentication token");
        }
        if(!EmailAndPasswordValidator.isStrongPassword(request.newPassword())){
            return new UserPasswordChangeResponseDto(false,HttpStatus.BAD_REQUEST,"Choose a strong password");
        }
        user.setPasswordHash(encoder.encode(request.newPassword()));
        repository.save(user);
        return new UserPasswordChangeResponseDto(true,HttpStatus.OK,"Password has been changed successfully");
    }



}
