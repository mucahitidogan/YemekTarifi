package com.yemektarifi.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorType {

    INTERNAL_ERROR(5100, "Sunucu Hatası", HttpStatus.INTERNAL_SERVER_ERROR),
    BAD_REQUEST(4000, "Parametre Hatası", HttpStatus.BAD_REQUEST),
    LOGIN_ERROR(4100, "Kullancı adı veya şifre hatalı", HttpStatus.BAD_REQUEST),
    PASSWORD_ERROR(4200, "Şifreler aynı değil", HttpStatus.BAD_REQUEST),
    USERNAME_DUPLICATE(4300, "Bu kullanıcı zaten kayıtlı", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(4400, "Böyle bir kullanıcı bulunamadı", HttpStatus.NOT_FOUND),
    ACTIVATE_CODE_ERROR(4500, "Aktivasyon kod hatası", HttpStatus.BAD_REQUEST),
    INVALID_TOKEN(4600,"Token hatası",HttpStatus.BAD_REQUEST),
    FOLLOW_ALREADY_EXIST(4700,"Böyle bir takip isteği daha önce oluştrulmuştur.",HttpStatus.BAD_REQUEST),
    USER_CANNOT_FOLLOW_HIMSELF(4800,"Kullanıcı kendisini takip edemez.",HttpStatus.BAD_REQUEST),
    OLD_PASSWORD_NOT_CORRECT(4800,"Girmiş olduğunuz mevcut şifreniz yanlıştır.",HttpStatus.BAD_REQUEST),
    RECIPE_NOT_FOUND(4900,"Girmiş olduğunuz tarif bulunamadı.",HttpStatus.BAD_REQUEST),
    YOUR_FAVORITELIST_IS_EMPTY(5000,"Favorinizde herhangi bir tarif bulunamadı.",HttpStatus.BAD_REQUEST),
    THIS_RECIPE_IS_ALREADY_ADDED_YOUR_FAVORITELIST(5100,"Bu tarif zaten favori listenizde eklidir.",HttpStatus.BAD_REQUEST);

    private int code;
    private String message;
    HttpStatus httpStatus;
}
