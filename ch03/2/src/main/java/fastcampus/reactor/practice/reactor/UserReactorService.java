package fastcampus.reactor.practice.reactor;

import fastcampus.reactor.practice.common.Article;
import fastcampus.reactor.practice.common.EmptyImage;
import fastcampus.reactor.practice.common.Image;
import fastcampus.reactor.practice.common.User;
import fastcampus.reactor.practice.common.repository.UserEntity;
import fastcampus.reactor.practice.reactor.repository.ArticleReactorRepository;
import fastcampus.reactor.practice.reactor.repository.FollowReactorRepository;
import fastcampus.reactor.practice.reactor.repository.ImageReactorRepository;
import fastcampus.reactor.practice.reactor.repository.UserReactorRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
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

        var imageMono = imageRepository.findById(userEntity.getProfileImageId())
                .map(imageEntity -> new Image(imageEntity.getId(), imageEntity.getName(), imageEntity.getUrl())
                ).onErrorReturn(new EmptyImage());

        var articlesMono = articleRepository.findAllByUserId(userEntity.getId())
                .map(
                        articleEntity -> new Article(articleEntity.getId(), articleEntity.getTitle(), articleEntity.getContent())
                ).collectList()
                .doOnSubscribe(subscription -> log.info("subscribe"))
                .contextWrite(context);

        var followCountMono = followRepository.countByUserId(userEntity.getId());

        return Mono.zip(imageMono, articlesMono, followCountMono)
                .map(
                        resultList -> {
                            Image image = resultList.getT1();
                            List<Article> articles = resultList.getT2();
                            Long followCount = resultList.getT3();

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
                        }
                );

    }
}
