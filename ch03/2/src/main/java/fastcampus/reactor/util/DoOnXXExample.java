package fastcampus.reactor.util;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
public class DoOnXXExample {

    public static void main(String[] args) {

        log.info("start main");

        Flux.range(1, 5)
                .map(value -> value * 2)
                .doOnNext(i -> log.info("doOnNext: {}", i))
                .doOnComplete(() -> log.info("doOnComplete"))
                .doOnSubscribe(s -> log.info("doOnSubscribe"))
                .doOnRequest(s -> log.info("doOnRequest: {}", s))
                .map(value -> value / 2)
                .subscribe();

        log.info("end main");
    }
}
