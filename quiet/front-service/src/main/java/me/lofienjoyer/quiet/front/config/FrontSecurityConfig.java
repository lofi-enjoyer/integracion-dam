package me.lofienjoyer.quiet.front.config;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import me.lofienjoyer.quiet.baseservice.config.SecurityContextRepository;
import me.lofienjoyer.quiet.baseservice.config.SecurityManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import reactor.core.publisher.Mono;

import java.net.URI;

import static org.springframework.http.HttpStatus.FOUND;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@RequiredArgsConstructor
@Order(0)
public class FrontSecurityConfig {

    private final SecurityManager securityManager;
    private final SecurityContextRepository securityContextRepository;

    @Primary
    @Bean
    public SecurityWebFilterChain securityFrontWebFilterChain(ServerHttpSecurity http) {
        return http.csrf().disable()
                .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
                .exceptionHandling()
                .authenticationEntryPoint(
                        (swe, e) ->
                                Mono.fromRunnable(
                                        () -> {
                                            swe.getResponse().setStatusCode(FOUND);
                                            swe.getResponse().getHeaders().setLocation(URI.create("/login"));
                                        }
                                )
                ).accessDeniedHandler(
                        (swe, e) ->
                                Mono.fromRunnable(
                                        () -> {
                                            swe.getResponse().setStatusCode(FOUND);
                                            swe.getResponse().getHeaders().setLocation(URI.create("/"));
                                        }
                                )
                )
                .and()
                .authenticationManager(securityManager)
                .securityContextRepository(securityContextRepository)
                .authorizeExchange()
                .anyExchange().permitAll()
                .and().build();
    }

}
