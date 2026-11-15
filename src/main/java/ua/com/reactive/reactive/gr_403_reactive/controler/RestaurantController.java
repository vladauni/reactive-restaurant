package ua.com.reactive.reactive.gr_403_reactive.controler;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import ua.com.reactive.reactive.gr_403_reactive.entity.Client;

@RestController
public class RestaurantController {

    @GetMapping("/clients")
    public Flux<Client> getClients() {
        Flux<Client> clients = Flux.just(
                        new Client(1L, "Олександр", "Мельник", "+380501234567"),
                        new Client(2L, "Марія", "Коваленко", "+380672345678"),
                        new Client(3L, "Андрій", "Бондаренко", "+380933456789")
                )
                .skip(0)
                .take(2);

        return clients;
    }
}