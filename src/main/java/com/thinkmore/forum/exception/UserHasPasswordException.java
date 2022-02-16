package com.thinkmore.forum.exception;

public class UserHasPasswordException extends RuntimeException{

    public  UserHasPasswordException(String message) {
        super(message);
    }
}
