package fastcampus.reactor.util;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
public class SkipExample {

    public static void main(String[] args) {
        Flux.range(1, 10)
                .skip(5)
                .doOnNext(i -> log.info("{}", i))
                .subscribe();
    }
}
