package me.lofienjoyer.quiet.gateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
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

                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
                }

                String authHeaders = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);

                if (authHeaders == null)
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);

                if (!authHeaders.startsWith("Bearer "))
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

                authHeaders = authHeaders.substring(7);

                try {
                    WebClient client = webClientBuilder.build();

                    String finalAuthHeaders = authHeaders;
                    Mono<String> monoResponse = client.get().uri("http://auth-service/validate", uriBuilder -> uriBuilder.queryParam("token", finalAuthHeaders).build())
                            .retrieve()
                            .bodyToMono(String.class);

                    return monoResponse
                            .onErrorResume(e -> Mono.fromRunnable(() -> {
                                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
                            }))
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
