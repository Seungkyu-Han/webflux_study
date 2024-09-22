package fastcampus.reactor.subscribe;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class ImmediateSchedulerExample {

    public static void main(String[] args) {

        Flux.create(sink -> {
            for(int i = 0; i < 5; i++){
                log.info("next: {}", i);
                sink.next(i);
            }
        }).subscribeOn(
                Schedulers.immediate()
        ).subscribe(value -> log.info("value: {}", value));
    }
}
