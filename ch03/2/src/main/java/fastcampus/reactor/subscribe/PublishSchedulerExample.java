package fastcampus.reactor.subscribe;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class PublishSchedulerExample {

    @SneakyThrows
    public static void main(String[] args) {
        Flux.create(sink -> {
            for(var i = 0; i < 5; i++){
                log.info("next: {}", i);
                sink.next(i);
            }
        }).publishOn(
                Schedulers.single()
        ).doOnNext(
                value -> log.info("doOnNext1: {}", value)
                ).publishOn(
                        Schedulers.boundedElastic()
                )
                .subscribe(
                        value -> log.info("doOnNext2: {}", value)
                );

        Thread.sleep(1000);
    }
}
