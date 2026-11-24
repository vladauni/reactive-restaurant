package ua.com.reactive.reactive.gr_403_reactive.handler;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ua.com.reactive.reactive.gr_403_reactive.entity.Administrator;
import ua.com.reactive.reactive.gr_403_reactive.entity.Bill;
import ua.com.reactive.reactive.gr_403_reactive.entity.Client;
import ua.com.reactive.reactive.gr_403_reactive.entity.MenuItem;
import ua.com.reactive.reactive.gr_403_reactive.entity.Order;
import ua.com.reactive.reactive.gr_403_reactive.entity.OrderItem;
import ua.com.reactive.reactive.gr_403_reactive.entity.OrderStatus;
import ua.com.reactive.reactive.gr_403_reactive.entity.Payment;

import java.util.List;

@Component
public class RestaurantHandler {

    public Mono<ServerResponse> home(ServerRequest request) {
        return ServerResponse
                .ok()
                .contentType(MediaType.TEXT_PLAIN)
                .body(BodyInserters.fromValue("Restaurant main page!"));
    }

    public Mono<ServerResponse> getClients(ServerRequest request) {

        String start = request
                .queryParam("start")
                .orElse("0");

        Flux<Client> clients = Flux.just(
                        new Client(1L, "Олександр", "Мельник", "+380501234567"),
                        new Client(2L, "Марія", "Коваленко", "+380672345678"),
                        new Client(3L, "Андрій", "Бондаренко", "+380933456789")
                )
                .skip(Long.valueOf(start))
                .take(2);

        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(clients, Client.class);
    }

    public Mono<ServerResponse> getAdministrators(ServerRequest request) {

        Flux<Administrator> admins = Flux.just(
                new Administrator(1L, "Олена", "Ткаченко"),
                new Administrator(2L, "Петро", "Савченко")
        );

        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(admins, Administrator.class);
    }

    public Mono<ServerResponse> getMenu(ServerRequest request) {

        Flux<MenuItem> menu = Flux.just(
                new MenuItem(1L, "Борщ з пампушками", 120.0),
                new MenuItem(2L, "Котлета по-київськи", 220.0),
                new MenuItem(3L, "Салат Цезар", 180.0),
                new MenuItem(4L, "Узвар", 50.0)
        );

        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(menu, MenuItem.class);
    }

    public Mono<ServerResponse> getOrders(ServerRequest request) {

        Flux<Order> orders = Flux.just(
                new Order(1L, 1L, null, List.of(
                        new OrderItem(1L, "Борщ з пампушками", 2, 120.0),
                        new OrderItem(4L, "Узвар", 2, 50.0)),
                        OrderStatus.NEW),
                new Order(2L, 2L, 1L, List.of(
                        new OrderItem(2L, "Котлета по-київськи", 1, 220.0),
                        new OrderItem(3L, "Салат Цезар", 1, 180.0)),
                        OrderStatus.IN_KITCHEN),
                new Order(3L, 3L, 2L, List.of(
                        new OrderItem(3L, "Салат Цезар", 1, 180.0),
                        new OrderItem(4L, "Узвар", 1, 50.0)),
                        OrderStatus.BILLED),
                new Order(4L, 1L, 1L, List.of(
                        new OrderItem(1L, "Борщ з пампушками", 1, 120.0),
                        new OrderItem(2L, "Котлета по-київськи", 1, 220.0),
                        new OrderItem(4L, "Узвар", 1, 50.0)),
                        OrderStatus.PAID)
        );

        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(orders, Order.class);
    }

    public Mono<ServerResponse> getBills(ServerRequest request) {

        Flux<Bill> bills = Flux.just(
                new Bill(1L, 3L, 230.0, false),
                new Bill(2L, 4L, 390.0, true)
        );

        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(bills, Bill.class);
    }

    public Mono<ServerResponse> getPayments(ServerRequest request) {

        Flux<Payment> payments = Flux.just(
                new Payment(1L, 2L, 390.0, "CARD")
        );

        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(payments, Payment.class);
    }
}