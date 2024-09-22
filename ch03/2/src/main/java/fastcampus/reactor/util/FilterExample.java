package fastcampus.reactor.util;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
public class FilterExample {

    @SneakyThrows
    public static void main(String[] args) {
        Flux.range(1, 5)
                .filter(i -> i % 2 == 0)
                .doOnNext(i -> log.info("{}", i))
                .subscribe();

        Thread.sleep(1000);
    }
}
