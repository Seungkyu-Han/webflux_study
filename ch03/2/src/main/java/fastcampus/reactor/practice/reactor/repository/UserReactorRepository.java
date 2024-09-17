package fastcampus.reactor.practice.reactor.repository;


import fastcampus.reactor.practice.common.repository.ArticleEntity;
import fastcampus.reactor.practice.common.repository.UserEntity;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.context.ContextView;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

@Slf4j
public class UserReactorRepository {
    private final Map<String, UserEntity> userMap;

    public UserReactorRepository() {
        var user = new UserEntity("1234", "taewoo", 32, "image#1000");

        userMap = Map.of("1234", user);
    }

    @SneakyThrows
    public Mono<UserEntity> findById(String userId) {
        return Mono.create(sink -> {
            log.info("userRepository.findById: {}", userId);
            try{
                Thread.sleep(1000);
            }catch(InterruptedException e){
                throw new RuntimeException(e);
            }
            UserEntity user = userMap.get(userId);
            if(user == null) {
                sink.success();
            }else{
                sink.success(user);
            }
        });
    }

}