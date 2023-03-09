package com.example.ekwateurProject.exception;

public class InvalidCodeException extends RuntimeException {
    public InvalidCodeException (String ExpiredOrNotExistCode) {
        super(ExpiredOrNotExistCode) ;
    }
}
