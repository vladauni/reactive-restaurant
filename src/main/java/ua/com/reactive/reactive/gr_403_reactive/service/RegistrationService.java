package ua.com.reactive.reactive.gr_403_reactive.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;
import ua.com.reactive.reactive.gr_403_reactive.dto.RegistrationRequest;
import ua.com.reactive.reactive.gr_403_reactive.entity.RolesHasUsers;
import ua.com.reactive.reactive.gr_403_reactive.entity.User;
import ua.com.reactive.reactive.gr_403_reactive.repository.RoleRepository;
import ua.com.reactive.reactive.gr_403_reactive.repository.RolesHasUsersRepository;
import ua.com.reactive.reactive.gr_403_reactive.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final RolesHasUsersRepository rolesHasUsersRepository;

    public Mono<User> register(RegistrationRequest request) {
        return userRepository.findByUsername(request.getUsername())
                .flatMap(existing -> Mono.<User>error(
                        new ResponseStatusException(HttpStatus.CONFLICT, "Користувач вже існує")))
                .switchIfEmpty(Mono.defer(() ->
                        userRepository.save(new User(null, request.getUsername(), request.getPassword()))
                                .flatMap(saved -> roleRepository.findByName("ROLE_User")
                                        .flatMap(role -> rolesHasUsersRepository
                                                .save(new RolesHasUsers(null, role.getId(), saved.getId())))
                                        .thenReturn(saved))));
    }

    public Mono<User> registerAdmin(RegistrationRequest request) {
        return register(request)   // створює користувача з ROLE_User
                .flatMap(saved -> roleRepository.findByName("ROLE_Admin")
                        .flatMap(role -> rolesHasUsersRepository
                                .save(new RolesHasUsers(null, role.getId(), saved.getId())))
                        .thenReturn(saved));
    }
}