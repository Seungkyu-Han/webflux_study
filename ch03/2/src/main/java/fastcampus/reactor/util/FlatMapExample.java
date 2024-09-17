package fastcampus.reactor.util;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class FlatMapExample {

    public static void main(String[] args) {
        Flux.range(1, 5)
                .flatMap(value -> Flux.range(1, 2)
                        .map(value2 -> value + ", "+ value2)
                        .publishOn(Schedulers.parallel()))
                .doOnNext(System.out::println)
                .subscribe();
    }
}
