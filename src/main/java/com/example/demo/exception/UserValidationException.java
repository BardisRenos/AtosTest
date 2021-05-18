package com.example.demo.exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * This class handle User Validation Exception cases.
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class UserValidationException extends Exception {

    /**
     * This method return an exception message when the condition is not satisfied.
     * @param message The given message what the exception will print.
     */
    public UserValidationException(String message) {
        super(message);
    }
}
