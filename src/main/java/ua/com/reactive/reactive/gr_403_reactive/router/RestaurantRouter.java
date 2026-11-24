package ua.com.reactive.reactive.gr_403_reactive.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import ua.com.reactive.reactive.gr_403_reactive.handler.RestaurantHandler;

@Configuration(proxyBeanMethods = false)
public class RestaurantRouter {

    @Bean
    public RouterFunction<ServerResponse> route(RestaurantHandler restaurantHandler) {
        return RouterFunctions
                .route(RequestPredicates.GET("/"), restaurantHandler::home)
                .andRoute(RequestPredicates.GET("/clients"), restaurantHandler::getClients)
                .andRoute(RequestPredicates.GET("/admins"), restaurantHandler::getAdministrators)
                .andRoute(RequestPredicates.GET("/menu"), restaurantHandler::getMenu)
                .andRoute(RequestPredicates.GET("/orders"), restaurantHandler::getOrders)
                .andRoute(RequestPredicates.GET("/bills"), restaurantHandler::getBills)
                .andRoute(RequestPredicates.GET("/payments"), restaurantHandler::getPayments);
    }
}