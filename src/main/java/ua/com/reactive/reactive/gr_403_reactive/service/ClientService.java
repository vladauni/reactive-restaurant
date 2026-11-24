package ua.com.reactive.reactive.gr_403_reactive.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ua.com.reactive.reactive.gr_403_reactive.entity.Client;
import ua.com.reactive.reactive.gr_403_reactive.repository.ClientRepository;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    public Flux<Client> findAll() {
        return clientRepository.findAll();
    }

    public Mono<Client> findById(Long id) {
        return clientRepository.findById(id);
    }

    public Mono<Client> create(Client client) {
        client.setId(null);                       // id генерує БД
        return clientRepository.save(client);
    }

    public Mono<Client> update(Long id, Client client) {
        return clientRepository.findById(id)
                .flatMap(existing -> {
                    existing.setFirstName(client.getFirstName());
                    existing.setLastName(client.getLastName());
                    existing.setPhone(client.getPhone());
                    return clientRepository.save(existing);
                });
    }

    public Mono<Void> delete(Long id) {
        return clientRepository.deleteById(id);
    }
}