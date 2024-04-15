package ru.skypro.homework.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class UserIllegalArgumentException extends RuntimeException{
    public UserIllegalArgumentException() {
    }

    public UserIllegalArgumentException(String message) {
        super(message);
    }
}
