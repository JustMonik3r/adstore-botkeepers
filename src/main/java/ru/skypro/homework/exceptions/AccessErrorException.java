package ru.skypro.homework.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class AccessErrorException extends RuntimeException{
    public AccessErrorException() {
    }

    public AccessErrorException(String message) {
        super(message);
    }
}