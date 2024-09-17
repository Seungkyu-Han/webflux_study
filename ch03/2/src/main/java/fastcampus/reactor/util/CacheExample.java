package fastcampus.reactor.util;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
public class CacheExample {

    public static void main(String[] args) throws InterruptedException {
        Flux<Object> flux = Flux.create(sink -> {
            for(int i = 0; i < 3; i++){
                log.info("next: {}", i);
                sink.next(i);
            }
            log.info("complete in publisher");
            sink.complete();
        }).cache();

        flux.subscribe(
                value -> log.info("value: {}", value)
        );

        flux.subscribe(
                value -> log.info("value: {}", value)
        );

        Thread.sleep(1000);

        log.info("end main");
    }
}
