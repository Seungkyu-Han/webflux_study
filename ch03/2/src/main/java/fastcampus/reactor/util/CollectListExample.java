package fastcampus.reactor.util;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
public class CollectListExample {

    @SneakyThrows
    public static void main(String[] args) {
        Flux.range(1, 5)
                .collectList()
                .doOnNext(value -> log.info("{}", value))
                .subscribe();
    }
}
