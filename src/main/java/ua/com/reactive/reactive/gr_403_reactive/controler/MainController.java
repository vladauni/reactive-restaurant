package ua.com.reactive.reactive.gr_403_reactive.controler;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ua.com.reactive.reactive.gr_403_reactive.entity.Message;
import ua.com.reactive.reactive.gr_403_reactive.service.MessageService;

@RestController
@RequestMapping("/controller")
@RequiredArgsConstructor
public class MainController {

    private final MessageService messageService;

    @GetMapping
    public Flux<Message> list() {
        return messageService.list();
    }

    @PostMapping
    public Mono<Message> create(@RequestBody Message message) {
        return messageService.addOne(message);
    }
}