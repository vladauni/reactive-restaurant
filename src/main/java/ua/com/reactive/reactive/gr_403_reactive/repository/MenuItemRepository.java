package ua.com.reactive.reactive.gr_403_reactive.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import ua.com.reactive.reactive.gr_403_reactive.entity.MenuItem;

public interface MenuItemRepository extends ReactiveCrudRepository<MenuItem, Long> {
}