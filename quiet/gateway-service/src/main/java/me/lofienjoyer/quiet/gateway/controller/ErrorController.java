package me.lofienjoyer.quiet.gateway.controller;

import me.lofienjoyer.quiet.gateway.exception.InvalidTokenException;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

@Component
public class ErrorController extends ResponseEntityExceptionHandler {

    @Bean
    public WebExceptionHandler exceptionHandler() {
        return (exchange, ex) -> {
            System.out.println("Exception caught!");
            if (ex instanceof InvalidTokenException) {
                System.out.println("Invalid token exception thrown.");
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            }

            return Mono.error(ex);
        };
    }

}
