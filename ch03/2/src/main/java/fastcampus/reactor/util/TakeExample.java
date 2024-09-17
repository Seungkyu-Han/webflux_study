package fastcampus.reactor.util;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
public class TakeExample {

    public static void main(String[] args) throws InterruptedException {
        Flux.range(1, 10)
                .take(5)
                .doOnNext(i -> log.info("{}", i))
                .subscribe();
        Flux.range(1, 10)
                .takeLast(5)
                .doOnNext(i -> log.info("{}", i))
                .subscribe();

        Thread.sleep(1000);
    }
}
