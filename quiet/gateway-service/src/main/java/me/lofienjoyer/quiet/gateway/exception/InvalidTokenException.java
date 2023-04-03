package me.lofienjoyer.quiet.gateway.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidTokenException extends ErrorResponseException {

    public InvalidTokenException(HttpStatusCode status) {
        super(status);
    }

}
