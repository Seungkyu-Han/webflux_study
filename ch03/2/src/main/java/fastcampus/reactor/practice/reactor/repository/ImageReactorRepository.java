package fastcampus.reactor.practice.reactor.repository;


import fastcampus.reactor.practice.common.repository.ImageEntity;
import fastcampus.reactor.practice.common.repository.UserEntity;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Slf4j
public class ImageReactorRepository {
    private final Map<String, ImageEntity> imageMap;

    public ImageReactorRepository() {
        imageMap = Map.of(
                "image#1000", new ImageEntity("image#1000", "profileImage", "https://dailyone.com/images/1000")
        );
    }

    @SneakyThrows
    public Mono<ImageEntity> findById(String id) {
        return Mono.create(sink -> {
           try{
               Thread.sleep(1000);
           }catch (InterruptedException e){
               throw new RuntimeException(e);
           }

           var image = imageMap.get(id);
           if(image == null) {
               sink.error(new RuntimeException("image not found"));
           }
           else{
               sink.success(image);
           }
        });
    }

    public Mono<ImageEntity> findWithContext(){
        return Mono.deferContextual(context -> {
            Optional<UserEntity> userEntityOptional = context.getOrEmpty("user");

            if(userEntityOptional.isEmpty())
                throw new RuntimeException("user not found");

            return Mono.just(userEntityOptional.get().getId());
        }).flatMap(this::findById);
    }
}
