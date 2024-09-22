package fastcampus.webflux.service;

import fastcampus.webflux.common.EmptyImage;
import fastcampus.webflux.common.Image;
import fastcampus.webflux.common.User;
import fastcampus.webflux.reactor.repository.UserReactorRepository;
import fastcampus.webflux.service.response.ImageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private WebClient webClient = WebClient.create("http://localhost:8081");

    private final UserReactorRepository userReactorRepository = new UserReactorRepository();

    public Mono<User> findById(String userId){
        return userReactorRepository.findById(userId)
                .flatMap(userEntity -> {
                    String imageId = userEntity.getProfileImageId();
                    return webClient.get().uri("/api/images/{imageId}", imageId)
                            .retrieve()
                            .bodyToMono(ImageResponse.class)
                            .map(
                                    response -> new Image(
                                            response.getId(),
                                            response.getName(),
                                            response.getUrl()
                                    )

                            ).onErrorComplete()
                            .switchIfEmpty(Mono.just(new EmptyImage()))
                            .map(image -> new User(
                                    userEntity.getId(),
                                    userEntity.getName(),
                                    userEntity.getAge(),
                                    Optional.of(image),
                                    List.of(),
                                    0L
                            ));
                        }
                );
    }
}
