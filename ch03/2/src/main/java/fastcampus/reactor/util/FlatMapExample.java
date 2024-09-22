package fastcampus.reactor.util;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class FlatMapExample {

    @SneakyThrows
    public static void main(String[] args) {
        Flux.range(1, 5)
                .flatMap(
                        value1 -> {
                            return Flux.range(1, 5)
                                    .map(value2 -> value1 + ", " + value2)
                                    .publishOn(Schedulers.parallel());
                        }
                ).doOnNext(
                        value -> log.info("{}", value)
                )
                .subscribe();

        Thread.sleep(1000);
    }
}
