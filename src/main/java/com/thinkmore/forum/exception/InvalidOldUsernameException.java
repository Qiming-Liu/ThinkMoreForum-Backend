package com.thinkmore.forum.exception;

public class InvalidOldUsernameException extends RuntimeException{
    public InvalidOldUsernameException(String message) {
        super(message);
    }
}
