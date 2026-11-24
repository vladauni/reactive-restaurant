package ua.com.reactive.reactive.gr_403_reactive.controler;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ua.com.reactive.reactive.gr_403_reactive.entity.MenuItem;
import ua.com.reactive.reactive.gr_403_reactive.service.MenuItemService;

@RestController
@RequestMapping("/api/menu-items")
@RequiredArgsConstructor
public class MenuItemController {

    private final MenuItemService menuItemService;

    @GetMapping
    public Flux<MenuItem> getAll() {
        return menuItemService.findAll();
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<MenuItem>> getById(@PathVariable Long id) {
        return menuItemService.findById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<MenuItem> create(@RequestBody MenuItem menuItem) {
        return menuItemService.create(menuItem);
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<MenuItem>> update(@PathVariable Long id, @RequestBody MenuItem menuItem) {
        return menuItemService.update(id, menuItem)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable Long id) {
        return menuItemService.findById(id)
                .flatMap(m -> menuItemService.delete(id)
                        .then(Mono.just(ResponseEntity.noContent().<Void>build())))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}