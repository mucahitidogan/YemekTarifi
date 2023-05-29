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
    TOKEN_NOT_CREATED(4700,"Failed to create token.",HttpStatus.BAD_REQUEST),
    AUTHORIZED_ERROR(4800,"You are not authorized to perform this action!",HttpStatus.BAD_REQUEST),
    CATEGORY_NOT_FOUND(4900,"This category was not found.",HttpStatus.BAD_REQUEST),
    THIS_CATEGORY_ALREADY_SAVED(4900,"This category is already registered in the system.",HttpStatus.BAD_REQUEST),
    RECIPE_NOT_FOUND(4900,"Recipe not found.",HttpStatus.BAD_REQUEST),
    COMMENT_NOT_FOUND(5000,"Comment not found.",HttpStatus.BAD_REQUEST),
    POINT_NOT_FOUND(5000,"Point not found.",HttpStatus.BAD_REQUEST);

    private int code;
    private String message;
    HttpStatus httpStatus;
}
