package ua.com.reactive.reactive.gr_403_reactive.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicate;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import ua.com.reactive.reactive.gr_403_reactive.handler.GreetingHandler;

import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration(proxyBeanMethods = false)
public class GreetingRouter {

    @Bean
    public RouterFunction<ServerResponse> greetingRoute(GreetingHandler greetingHandler) {

        RequestPredicate accept = accept(MediaType.APPLICATION_JSON);
        return RouterFunctions
                .route(RequestPredicates.GET("/users").and(accept), greetingHandler::users)
                .andRoute(RequestPredicates.GET("/admin"), greetingHandler::admin);
    }
}