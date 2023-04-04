package me.lofienjoyer.quiet.auth.config;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class ExceptionConfig {

    @ExceptionHandler(ResponseStatusException.class)
    @ResponseBody
    public ResponseEntity<String> errorHandler(ResponseStatusException exception) {
        return ResponseEntity.status(exception.getStatusCode())
                .body(exception.getReason());
    }

}
