package fastcampus.reactor.reactor;


import fastcampus.reactor.common.Article;
import fastcampus.reactor.common.EmptyImage;
import fastcampus.reactor.common.Image;
import fastcampus.reactor.common.User;
import fastcampus.reactor.common.repository.ArticleEntity;
import fastcampus.reactor.common.repository.UserEntity;
import fastcampus.reactor.reactor.repository.ArticleReactorRepository;
import fastcampus.reactor.reactor.repository.FollowReactorRepository;
import fastcampus.reactor.reactor.repository.ImageReactorRepository;
import fastcampus.reactor.reactor.repository.UserReactorRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;
import reactor.util.context.ContextView;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Slf4j
@RequiredArgsConstructor
public class UserReactorService {
    private final UserReactorRepository userRepository;
    private final ArticleReactorRepository articleRepository;
    private final ImageReactorRepository imageRepository;
    private final FollowReactorRepository followRepository;

    @SneakyThrows
    public Mono<User> getUserById(String id) {
        return userRepository.findById(id)
                .flatMap(this::getUser);
    }

    @SneakyThrows
    private Mono<User> getUser(UserEntity userEntity) {
        Context context = Context.of("user", userEntity);

        var imageMono = imageRepository.findById(userEntity.getProfileImageId())
                .map(imageEntity ->
                                new Image(imageEntity.getId(), imageEntity.getName(), imageEntity.getUrl())
                        )
                .onErrorReturn(new EmptyImage());

        var articlesMono = articleRepository.findAllWithContext()
                .map(articleEntity ->
                        new Article(articleEntity.getId(), articleEntity.getTitle(), articleEntity.getContent())

                ).collectList().doOnSubscribe(subscription -> {
                    log.info("subscribe articleRepository");
                }).contextWrite(context);

        var followCountMono = followRepository.countByUserId(userEntity.getId());

        return Mono.zip(imageMono, articlesMono, followCountMono)
                .map(
                        resultList -> {
                            Image image = resultList.getT1();
                            List<Article> articles = resultList.getT2();
                            Long followCount = resultList.getT3();


                            Optional<Image> imageOptional = Optional.empty();
                            if(!(image instanceof EmptyImage)) {
                                imageOptional = Optional.of((Image) image);
                            }

                            return new User(
                                    userEntity.getId(),
                                    userEntity.getName(),
                                    userEntity.getAge(),
                                    imageOptional,
                                    articles,
                                    followCount
                            );
                        }
                );
    }

}
