package com.thinkmore.forum.exception;

import com.thinkmore.forum.dto.ForumErrorDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
@Slf4j
public class ControllerExceptionHandler {

    public ResponseEntity<ForumErrorDto> handleNotFoundException(UserNotFoundException e) {
        log.info("Username is not found.", e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ForumErrorDto("USER_NOT_FOUND", "User is not found"));
    }

    public ResponseEntity<ForumErrorDto> handleInvalidPasswordException(InvalidPasswordException e) {
        log.info("Invalid password.", e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ForumErrorDto("INVALID_PASSWORD", "Invalid password"));
    }

    public ResponseEntity<ForumErrorDto> handleInvalidOldPasswordException(InvalidOldPasswordException e) {
        log.info("Password is wrong.", e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ForumErrorDto("PASSWORD_IS_WRONG", "Password is wrong"));
    }

    public ResponseEntity<ForumErrorDto> handleInvalidJwtException(InvalidJwtException e) {
        log.info("Invalid Jwt Token.", e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ForumErrorDto("INVALID_JWT_TOKEN", "Invalid Jwt Token"));
    }

    public ResponseEntity<ForumErrorDto> handlePasswordExistedException(UserHasPasswordException e) {
        log.info("User has a password", e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ForumErrorDto("USER_HAS_PASSWORD", "User has a password"));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ForumErrorDto> handleException(Exception exception, WebRequest request){
        log.error("There is Exception occurred",exception);
        return new ResponseEntity(new ForumErrorDto(exception.getMessage(), "error"), HttpStatus.SERVICE_UNAVAILABLE);
    }
}
