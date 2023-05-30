package me.lofienjoyer.quiet.baseservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpCookie;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * Security context repository to handle authentication
 */
@Component
public class SecurityContextRepository implements ServerSecurityContextRepository {

    @Autowired
    SecurityManager securityManager;

    /**
     * Saves the security context
     * @param exchange Request connection
     * @param context Security context
     * @return Empty mono object
     */
    @Override
    public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
        throw new RuntimeException("Not supported");
    }

    /**
     * Loads the security context
     * @param exchange Request connection
     * @return Mono with security context object
     */
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
