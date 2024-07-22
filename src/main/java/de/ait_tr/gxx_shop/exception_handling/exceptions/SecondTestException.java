package de.ait_tr.gxx_shop.exception_handling.exceptions;
/*
@date 22.07.2024
@author Sergey Bugaienko
*/

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Second Test Exception message")
public class SecondTestException extends RuntimeException{

    public SecondTestException(String message) {
        super(message);
    }
}
