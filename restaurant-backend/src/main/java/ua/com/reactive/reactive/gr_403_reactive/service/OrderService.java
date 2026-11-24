package ua.com.reactive.reactive.gr_403_reactive.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ua.com.reactive.reactive.gr_403_reactive.entity.Order;
import ua.com.reactive.reactive.gr_403_reactive.repository.OrderRepository;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public Flux<Order> findAll() {
        return orderRepository.findAll();
    }

    public Mono<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

    public Mono<Order> create(Order order) {
        order.setId(null);                        // id генерує БД
        return orderRepository.save(order);
    }

    public Mono<Order> update(Long id, Order order) {
        return orderRepository.findById(id)
                .flatMap(existing -> {
                    existing.setClientId(order.getClientId());
                    existing.setAdministratorId(order.getAdministratorId());
                    existing.setStatus(order.getStatus());
                    return orderRepository.save(existing);
                });
    }

    public Mono<Void> delete(Long id) {
        return orderRepository.deleteById(id);
    }
}