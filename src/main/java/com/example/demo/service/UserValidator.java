package com.example.demo.service;
import com.example.demo.exception.UserValidationException;
import com.example.demo.model.User;
import org.springframework.stereotype.Component;

/**
 * The User validator class which validate if a User can create an account. Or can rise an exception if the user can
 * not meet some criteria.
 */
@Component
public class UserValidator {

    /**
     * The validate method checks if the user is greater than 18 years old and if is from France. Otherwise,
     * rise a UserValidationException.
     * @param user Class.
     * @throws UserValidationException
     */
    public void validate(User user) throws UserValidationException {
        if (user.getAge() <= 18 || !"France".equals(user.getCountry())) {
            throw new UserValidationException("User must be older than 18 years old and live in France");
        }
    }
}
