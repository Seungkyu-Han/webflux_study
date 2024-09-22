package fastcampus.reactor.util;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
public class MapExample {

    public static void main(String[] args) {
        Flux.range(1, 5)
                .map(value -> value * 2)
                .doOnNext(value -> log.info("value1: {}", value))
                .subscribe();

        Flux.range(1, 5)
                .mapNotNull(
                        value -> {
                            if(value % 2 == 0)
                                return value;
                            return null;
                        }
                ).doOnNext(value -> log.info("value2: {}", value))
                .subscribe();
    }
}
