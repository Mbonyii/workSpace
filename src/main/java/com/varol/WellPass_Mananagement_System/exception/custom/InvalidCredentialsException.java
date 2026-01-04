package com.varol.WellPass_Mananagement_System.exception.custom;


public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException(String message) {
        super(message);
    }
}