package fastcampus.reactor.util;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
public class DoOnXXExample {

    @SneakyThrows
    public static void main(String[] args) {
        Flux.range(1, 5)
                .map(value -> value * 2)
                .doOnNext(
                        value -> log.info("value: {}", value)
                )
                .doOnComplete(
                        () -> log.info("doOnComplete")
                )
                .doOnSubscribe(
                        sub -> log.info("doOnSubscribe: {}", sub.toString())
                )
                .doOnRequest(
                        value -> log.info("doOnRequest: {}", value)
                )
                .map(value -> value / 2)
                .subscribe();
    }
}
