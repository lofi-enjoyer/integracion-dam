package me.lofienjoyer.quiet.postservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class SecurityContextRepository implements ServerSecurityContextRepository {

    @Autowired
    SecurityManager securityManager;

    @Override
    public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
        throw new RuntimeException("Not supported");
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange exchange) {
        HttpCookie tokenCookie = exchange.getRequest().getCookies().getFirst("token");

        if (tokenCookie == null) {
            return Mono.empty();
        }

        String token = tokenCookie.getValue();

        Authentication authentication = new UsernamePasswordAuthenticationToken(token, token);

        return securityManager.authenticate(authentication)
                .map(SecurityContextImpl::new);
    }

}
