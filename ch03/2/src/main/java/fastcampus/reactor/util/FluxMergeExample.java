package fastcampus.reactor.util;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.time.Duration;

@Slf4j
public class FluxMergeExample {

    public static void main(String[] args) throws InterruptedException {
        var flux1 = Flux.range(1, 3)
                .doOnSubscribe(value -> log.info("doOnSubscribe1"))
                .delayElements(Duration.ofMillis(100));

        var flux2 = Flux.range(10, 3)
                .doOnSubscribe(value -> log.info("doOnSubscribe2"))
                .delayElements(Duration.ofMillis(100));

        Flux.merge(flux1, flux2)
                .doOnNext(value -> log.info("doOnNext: {}", value))
                .subscribe();

        Thread.sleep(5000);
    }
}
