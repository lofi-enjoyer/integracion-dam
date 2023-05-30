package me.lofienjoyer.quiet.baseservice.config;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

/**
 * Component to handle exceptions
 */
@RestControllerAdvice
public class ErrorController {

    /**
     * @param exception Exception received
     * @return Response entity to send to the client
     */
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<String> handleError(ResponseStatusException exception) {
        return ResponseEntity.status(exception.getStatusCode())
                .body(exception.getReason());
    }

}
