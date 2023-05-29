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
    USER_NOT_FOUND(4400, "No such user found", HttpStatus.NOT_FOUND),
    THIS_EMAIL_IS_ALREADY_REGISTER(4500,"This email address is already registered in the system.!",HttpStatus.BAD_REQUEST),
    ACTIVATE_CODE_ERROR(4600, "Activation code error", HttpStatus.BAD_REQUEST),
    INVALID_TOKEN(4700,"Token error!",HttpStatus.BAD_REQUEST),
    TOKEN_NOT_CREATED(4800,"Failed to create token.",HttpStatus.BAD_REQUEST),
    ADDRESS_NOT_FOUND(4800,"Address not found.",HttpStatus.BAD_REQUEST);

    private int code;
    private String message;
    HttpStatus httpStatus;
}
