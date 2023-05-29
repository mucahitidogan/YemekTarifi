package com.yemektarifi.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorType {

    INTERNAL_ERROR(5100, "Server Error", HttpStatus.INTERNAL_SERVER_ERROR),
    BAD_REQUEST(4000, "Parameter Error", HttpStatus.BAD_REQUEST),
    LOGIN_ERROR(4100, "Username or password is wrong", HttpStatus.BAD_REQUEST),
    PASSWORD_ERROR(4200, "Passwords are not the same", HttpStatus.BAD_REQUEST),
    USERNAME_DUPLICATE(4300, "This user is already registered", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(4400, "User not found!", HttpStatus.NOT_FOUND),
    ACTIVATE_CODE_ERROR(4500, "Activation code error", HttpStatus.BAD_REQUEST),
    INVALID_TOKEN(4600,"Token error!",HttpStatus.BAD_REQUEST),
    OLD_PASSWORD_NOT_CORRECT(4700,"The current password you entered is incorrect.",HttpStatus.BAD_REQUEST),
    RECIPE_NOT_FOUND(4800,"The recipe you entered could not be found.",HttpStatus.BAD_REQUEST),
    YOUR_FAVORITELIST_IS_EMPTY(4900,"No recipe found in your favorite.",HttpStatus.BAD_REQUEST),
    THIS_RECIPE_IS_ALREADY_ADDED_YOUR_FAVORITELIST(5000,"This recipe is already added to your favorites list.",HttpStatus.BAD_REQUEST);

    private int code;
    private String message;
    HttpStatus httpStatus;
}
