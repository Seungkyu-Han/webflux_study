package fastcampus.reactor.reactor;


import fastcampus.reactor.common.Article;
import fastcampus.reactor.common.EmptyImage;
import fastcampus.reactor.common.Image;
import fastcampus.reactor.common.User;
import fastcampus.reactor.common.repository.UserEntity;
import fastcampus.reactor.reactor.repository.ArticleReactorRepository;
import fastcampus.reactor.reactor.repository.FollowReactorRepository;
import fastcampus.reactor.reactor.repository.ImageReactorRepository;
import fastcampus.reactor.reactor.repository.UserReactorRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

import java.util.List;
import java.util.Optional;

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

        var imageMono = imageRepository.findWithContext()
                .map(imageEntity -> new Image(imageEntity.getId(), imageEntity.getName(), imageEntity.getUrl()))
                .onErrorReturn(new EmptyImage());

        log.info(imageMono.toString());


        var articlesMono = articleRepository.findAllWithContext()
                .map(articleEntity ->
                        new Article(articleEntity.getId(), articleEntity.getTitle(), articleEntity.getContent())
                ).collectList();

        var followsCountMono = followRepository.countWithUser();

        return Mono.zip(imageMono, articlesMono, followsCountMono)
                .map(resultTuple -> {
                    Image image = resultTuple.getT1();
                    List<Article> articles = resultTuple.getT2();
                    Long followCount = resultTuple.getT3();

                    Optional<Image> imageOptional = Optional.empty();

                    if(!(image instanceof EmptyImage)) {
                        imageOptional = Optional.of(image);
                    }

                    return new User(
                            userEntity.getId(),
                            userEntity.getName(),
                            userEntity.getAge(),
                            imageOptional,
                            articles,
                            followCount
                    );

                }).contextWrite(context);

    }
}
