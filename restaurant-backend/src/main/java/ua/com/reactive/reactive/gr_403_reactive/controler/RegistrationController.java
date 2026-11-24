package ua.com.reactive.reactive.gr_403_reactive.controler;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Mono;
import ua.com.reactive.reactive.gr_403_reactive.dto.RegistrationRequest;
import ua.com.reactive.reactive.gr_403_reactive.service.RegistrationService;

@Controller
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    // Реєстрація через JSON (Postman)
    @PostMapping(value = "/registration", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Mono<ResponseEntity<String>> registerJson(@RequestBody RegistrationRequest request) {
        return registrationService.register(request)
                .map(u -> ResponseEntity.status(HttpStatus.CREATED)
                        .body("Користувача " + u.getUsername() + " зареєстровано"));
    }
    @PostMapping(value = "/admins", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Mono<ResponseEntity<String>> registerAdmin(@RequestBody RegistrationRequest request) {
        return registrationService.registerAdmin(request)
                .map(u -> ResponseEntity.status(HttpStatus.CREATED)
                        .body("Адміністратора " + u.getUsername() + " створено"));
    }


    @GetMapping("/registration")
    public String page() {
        return "registration";
    }

    @PostMapping(value = "/registration", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public Mono<String> registerForm(@ModelAttribute RegistrationRequest request) {
        return registrationService.register(request).thenReturn("redirect:/login");
    }
}