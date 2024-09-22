package fastcampus.reactor.util;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.time.Duration;

@Slf4j
public class FluxMergeSequentialExample {


    @SneakyThrows
    public static void main(String[] args) {
        var flux1 = Flux.range(1, 3)
                .doOnSubscribe(
                        value -> log.info("value1: {}", value)
                )
                .delayElements(Duration.ofMillis(100));

        var flux2 = Flux.range(10, 3)
                .doOnSubscribe(
                        value -> log.info("value2: {}", value)
                )
                .delayElements(Duration.ofMillis(100));

        Flux.mergeSequential(flux1, flux2)
                .doOnNext(value -> log.info("value: {}", value))
                .subscribe();

        Thread.sleep(1000);
    }
}
