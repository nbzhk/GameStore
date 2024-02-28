package com.example.gamestore.entities.userEntities;

import com.example.gamestore.exeptions.ValidationException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterUserDTO  {

    private static final String PASSWORD_REGEX = "^(?!\\s)(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{6,}$";
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9]+@([a-zA-Z0-9]+.[a-z]+)+$";
    private String email;
    private String password;
    private String confirmPassword;
    private String fullName;

    //commandData[0] is the commandName and is used in the ConsoleRunner
    public RegisterUserDTO(String[] commandData) {
        this.email = validateEmail(commandData[1]);
        this.password = validatePassword(commandData[2]);
        this.confirmPassword = confirm(commandData[2], commandData[3]);
        this.fullName = commandData[4];


    }

    private String confirm(String initialPassword, String confirm) {
        if (!confirm.equals(initialPassword)) {
            throw new ValidationException("Password does not match");
        }

        return confirm;
    }

    private String validatePassword(String password) {
        Pattern pattern = Pattern.compile(PASSWORD_REGEX);
        Matcher matcher = pattern.matcher(password);

        if (!matcher.find()) {
            throw new ValidationException("Password must be at " +
                    "least 6 symbols and must contain at least one " +
                    "uppercase, one lowercase letter and one digit.");
        }

        return password;
    }

    private String validateEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);

        if (!matcher.find()) {
            throw new ValidationException("Email must contain @ and a period.");
        }

        return email;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public String getFullName() {
        return fullName;
    }
}
