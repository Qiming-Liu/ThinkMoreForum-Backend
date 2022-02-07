package com.thinkmore.forum.exception;

public class InvalidOldEmailException extends RuntimeException{
    public InvalidOldEmailException(String message) {
        super(message);
    }
}