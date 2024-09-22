package fastcampus.reactor.subscribe;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class NewSingleSchedulerExample {

    @SneakyThrows
    public static void main(String[] args) {
        for(int i = 0; i < 100; i++){
            var newSingle = Schedulers.newSingle("newSingle");

            final int idx = i;

            Flux.create(sink -> {
                log.info("next: {}", idx);
                sink.next(idx);
            }).subscribeOn(
                    newSingle
            ).subscribe(
                    value -> {
                        log.info("value: {}", value);
                        newSingle.dispose();
                    }
            );
        }

        Thread.sleep(1000);
    }
}
