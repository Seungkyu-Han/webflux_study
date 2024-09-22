package fastcampus.reactor.util;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
public class TakeExample {

    @SneakyThrows
    public static void main(String[] args) {
        Flux.range(1, 10)
                .take(5)
                .doOnNext(i -> log.info("take: {}", i))
                .subscribe();

        Flux.range(1, 10)
                .takeLast(5)
                .doOnNext(i -> log.info("takeLast: {}", i))
                .subscribe();
    }
}
