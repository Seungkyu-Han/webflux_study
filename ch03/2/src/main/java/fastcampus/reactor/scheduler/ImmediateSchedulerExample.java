package fastcampus.reactor.scheduler;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class ImmediateSchedulerExample {

    public static void main(String[] args) {
        log.info("start main");

        Flux.create(sink -> {
            for(int i = 1; i <= 5; i++){
                log.info("next: {}", i);
                sink.next(i);
            }
        }).subscribeOn(Schedulers.immediate())
                        .subscribe(i -> log.info("value: {}", i));

        log.info("end main");
    }
}
