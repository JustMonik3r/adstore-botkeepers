package ru.skypro.homework.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class AdNotFoundException extends RuntimeException {
    public AdNotFoundException(String message) {
        super(message);
    }
}
