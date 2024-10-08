package fastcampus.reactor.reactor.repository;


import fastcampus.reactor.common.repository.UserEntity;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Slf4j
public class FollowReactorRepository {
    private Map<String, Long> userFollowCountMap;

    public FollowReactorRepository() {
        userFollowCountMap = Map.of("1234", 1000L);
    }

    @SneakyThrows
    public Mono<Long> countByUserId(String userId) {
        return Mono.create(sink -> {
            log.info("FollowRepository.countByUserId: {}", userId);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            sink.success(userFollowCountMap.getOrDefault(userId, 0L));
        });
    }

    public Mono<Long> countWithUser(){
        return Mono.deferContextual(contextView -> {
            Optional<UserEntity> userEntityOptional = contextView.getOrEmpty("user");
            if (userEntityOptional.isEmpty()) {
                throw new RuntimeException("user not found");
            }

            var user = userEntityOptional.get();

            return Mono.just(user.getId());
        }).flatMap(this::countByUserId);
    }
}
