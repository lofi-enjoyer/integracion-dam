package me.lofienjoyer.quiet.auth.config;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

/**
 * Configuration for handling errors
 */
@RestControllerAdvice
public class ErrorController {

    /**
     * @param exception Exception received
     * @return ResponseEntity to send to the client
     */
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<String> handleError(ResponseStatusException exception) {
        return ResponseEntity.status(exception.getStatusCode())
                .body(exception.getReason());
    }

}
