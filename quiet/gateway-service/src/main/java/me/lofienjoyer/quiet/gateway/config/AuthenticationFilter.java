package me.lofienjoyer.quiet.gateway.config;

import me.lofienjoyer.quiet.gateway.exception.InvalidTokenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    @Autowired
    private RouteValidator routeValidator;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    private RestTemplate restTemplate;

    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            if (routeValidator.isSecured.test(exchange.getRequest())) {

                if (!exchange.getRequest().getCookies().containsKey("token")) {
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
                }

                String tokenCookie = exchange.getRequest().getCookies().getFirst("token").getValue();

                try {
                    WebClient client = webClientBuilder.build();

                    Mono<String> monoResponse = client.get().uri("http://auth-service/validate", uriBuilder -> uriBuilder.queryParam("token", tokenCookie).build())
                            .retrieve()
                            .bodyToMono(String.class);

                    return monoResponse
                            .onErrorResume(e -> Mono.error(new ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS)))

                            .flatMap(response -> {
                                return chain.filter(exchange);
                            });
                } catch (Exception e) {
                    System.out.println("Error validating token!");
                    e.printStackTrace();
                }
            }

            return chain.filter(exchange);
        });
    }

    public static class Config {

    }

}
