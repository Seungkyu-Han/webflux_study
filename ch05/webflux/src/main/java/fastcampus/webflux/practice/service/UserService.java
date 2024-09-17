package fastcampus.webflux.practice.service;

import fastcampus.webflux.practice.element.Image;
import fastcampus.webflux.practice.element.User;
import fastcampus.webflux.practice.repository.UserReactorRepository;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserReactorRepository userRepository = new UserReactorRepository();

    private WebClient webClient = WebClient.create("http://localhost:8081");

    public Mono<User> findById(String userId){
        return userRepository.findById(userId)
                .flatMap(userEntity ->{
                    String imageId = userEntity.getProfileImageId();

                    return webClient.get()
                            .uri("/api/images/{}", imageId)
                            .retrieve()
                            .toEntity(ImageResponse.class)
                            .map(HttpEntity::getBody)
                            .map(imageResponse -> new Image(
                                    imageResponse.getId(),
                                    imageResponse.getName(),
                                    imageResponse.getUrl()
                            )).switchIfEmpty(Mono.just(new Image("", "", "")))
                            .map(
                                    image -> new User(
                                            userEntity.getId(),
                                            userEntity.getName(),
                                            userEntity.getAge(),
                                            Optional.empty(),
                                            List.of(),
                                            0L
                                    )
                            );
                });

    }
}
