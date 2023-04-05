package me.lofienjoyer.quiet.postservice.config;

import me.lofienjoyer.quiet.basemodel.dto.UserInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class SecurityManager implements ReactiveAuthenticationManager {

    @Autowired
    WebClient.Builder webClientBuilder;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String token = authentication.getCredentials().toString();

        return webClientBuilder.build().get().uri("http://auth-service/getuser",
                        uriBuilder -> uriBuilder
                                .queryParam("token", token)
                                .build()
                )
                .cookie("token", token)
                .retrieve()
                .bodyToMono(UserInfoDto.class)

                .map(userInfoDto -> {
                    Set<GrantedAuthority> authorities = userInfoDto.getAuthorities().stream()
                            .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                            .collect(Collectors.toSet());;

                    return new UsernamePasswordAuthenticationToken(
                            userInfoDto.getEmail(),
                            null,
                            authorities
                    );
                });
    }

}
