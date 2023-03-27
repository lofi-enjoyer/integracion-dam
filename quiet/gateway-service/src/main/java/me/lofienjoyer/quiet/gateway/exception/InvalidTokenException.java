package me.lofienjoyer.quiet.gateway.exception;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.ErrorResponseException;

public class InvalidTokenException extends ErrorResponseException {

    public InvalidTokenException(HttpStatusCode status) {
        super(status);
    }

}
