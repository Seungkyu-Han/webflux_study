package fastcampus.reactor.practice.reactor.repository;


import fastcampus.reactor.practice.common.repository.ArticleEntity;
import fastcampus.reactor.practice.common.repository.UserEntity;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.context.ContextView;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
public class ArticleReactorRepository {
    private static List<ArticleEntity> articleEntities;

    public ArticleReactorRepository() {
        articleEntities = List.of(
                new ArticleEntity("1", "소식1", "내용1", "1234"),
                new ArticleEntity("2", "소식2", "내용2", "1234"),
                new ArticleEntity("3", "소식3", "내용3", "10000")
        );
    }

    @SneakyThrows
    public Flux<ArticleEntity> findAllByUserId(String userId) {
        return Flux.create(sink -> {
            log.info("ArticleRepository.findAllByUserId: {}", userId);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            articleEntities.stream()
                    .filter(articleEntity -> articleEntity.getUserId().equals(userId))
                    .forEach(sink::next);

            sink.complete();
        });
    }

    public Flux<ArticleEntity> findAllWithContext() {
        return Flux.deferContextual(contextView -> {
            Optional<UserEntity> userOptional = contextView.getOrEmpty("user");

            if (userOptional.isEmpty()) {
                throw new RuntimeException("user not found");
            }

            return Mono.just(userOptional.get().getId());
        }).flatMap(this::findAllByUserId);
    }
}
