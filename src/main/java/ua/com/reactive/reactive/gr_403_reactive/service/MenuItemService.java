package ua.com.reactive.reactive.gr_403_reactive.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ua.com.reactive.reactive.gr_403_reactive.entity.MenuItem;
import ua.com.reactive.reactive.gr_403_reactive.repository.MenuItemRepository;

@Service
@RequiredArgsConstructor
public class MenuItemService {

    private final MenuItemRepository menuItemRepository;

    public Flux<MenuItem> findAll() {
        return menuItemRepository.findAll();
    }

    public Mono<MenuItem> findById(Long id) {
        return menuItemRepository.findById(id);
    }

    public Mono<MenuItem> create(MenuItem menuItem) {
        menuItem.setId(null);                     // id генерує БД
        return menuItemRepository.save(menuItem);
    }

    public Mono<MenuItem> update(Long id, MenuItem menuItem) {
        return menuItemRepository.findById(id)
                .flatMap(existing -> {
                    existing.setName(menuItem.getName());
                    existing.setPrice(menuItem.getPrice());
                    return menuItemRepository.save(existing);
                });
    }

    public Mono<Void> delete(Long id) {
        return menuItemRepository.deleteById(id);
    }
}