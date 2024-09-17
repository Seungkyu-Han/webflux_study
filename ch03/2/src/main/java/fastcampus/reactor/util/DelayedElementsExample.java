package fastcampus.reactor.util;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

@Slf4j
public class DelayedElementsExample {

    public static void main(String[] args) throws InterruptedException {
        log.info("start main");

        Flux.create(
                sink -> {
                    for(int i = 0; i < 5; i++){
                        try{
                            Thread.sleep(500);
                        }catch (InterruptedException e){
                            sink.error(e);
                        }
                        sink.next(i);
                    }
                    sink.complete();
                }
        ).delayElements(Duration.ofMillis(500))
                .doOnNext(value -> log.info("doOnNext: {}", value))
                .subscribeOn(Schedulers.single())
                .subscribe();

        Thread.sleep(6000);

        log.info("end main");
    }
}
