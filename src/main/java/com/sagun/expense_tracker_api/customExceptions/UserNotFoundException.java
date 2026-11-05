package com.sagun.expense_tracker_api.customExceptions;

public class UserNotFoundException extends RuntimeException{
     public UserNotFoundException(String message){
         super(message);
     }
}
