package com.sagun.expense_tracker_api.exceptionHandler;

import com.sagun.expense_tracker_api.customExceptions.UserNotFoundException;
import com.sun.jdi.request.InvalidRequestStateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionsHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    ResponseEntity<Map<String, Object>>handleIllegalArgumentException(IllegalArgumentException exception){
        Map<String, Object> messages = new HashMap<>();
        messages.put("status", HttpStatus.BAD_REQUEST);
        messages.put("error",exception.getMessage());
        return ResponseEntity.badRequest().body(messages);

    }

    @ExceptionHandler(UserNotFoundException.class)
    ProblemDetail userNotFound(UserNotFoundException exception){
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,exception.getMessage());
    }


    @ExceptionHandler(InvalidRequestStateException.class)
    ProblemDetail invalid(InvalidRequestStateException exception){
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,exception.getMessage());
    }

    @ExceptionHandler(DisabledException.class)
    ProblemDetail disabled(DisabledException exception){
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,exception.getMessage());
    }

}
