package fastcampus.webflux.practice.controller;

import fastcampus.webflux.practice.handler.dto.UserResponse;
import fastcampus.webflux.practice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{userId}")
    public Mono<UserResponse> getUserById(
            @PathVariable String userId) {
        return ReactiveSecurityContextHolder
                .getContext()
                .flatMap(
                        context -> {
                            String name = context.getAuthentication().getName();
                            if (!name.equals(userId))
                                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);

                            return userService.findById(userId)
                                    .map(user -> UserResponse.builder()
                                            .id(user.getId())
                                            .name(user.getName())
                                            .age(user.getAge())
                                            .followCount(user.getFollowCount())
                                            .build());

                        }
                );


    }
}
