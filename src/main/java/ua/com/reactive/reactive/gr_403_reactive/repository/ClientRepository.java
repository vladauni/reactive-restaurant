package ua.com.reactive.reactive.gr_403_reactive.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import ua.com.reactive.reactive.gr_403_reactive.entity.Client;

public interface ClientRepository extends ReactiveCrudRepository<Client, Long> {
}