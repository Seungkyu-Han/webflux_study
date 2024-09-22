package fastcampus.reactor.util;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
public class SkipExample {

    @SneakyThrows
    public static void main(String[] args) {
        Flux.range(1, 10)
                .skip(5)
                .doOnNext(i -> log.info("skip: {}", i))
                .subscribe();

        Flux.range(1, 10)
                .skipLast(5)
                .doOnNext(i -> log.info("skipLast: {}", i))
                .subscribe();
    }
}
