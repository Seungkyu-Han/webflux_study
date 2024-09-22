package fastcampus.reactor.util;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
public class CacheExample {

    @SneakyThrows
    public static void main(String[] args) {
        Flux<Object> flux = Flux.create(sink -> {
            for(int i = 0; i < 5; i++){
                log.info("next: {}", i);
                sink.next(i);
            }
            log.info("complete in publisher");
            sink.complete();
        }).cache();

        for(int i = 0; i < 2; i++){
            flux.subscribe(
                    value -> log.info("value1: {}", value),
                    null,
                    () -> log.info("complete")
            );
        }
    }
}
