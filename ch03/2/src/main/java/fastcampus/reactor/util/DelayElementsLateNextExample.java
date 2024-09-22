package fastcampus.reactor.util;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

@Slf4j
public class DelayElementsLateNextExample {

    @SneakyThrows
    public static void main(String[] args) {
        Flux.create(sink -> {
            for(int i = 0; i < 5; i++){
                try{
                    Thread.sleep(1000);
                }catch(InterruptedException e){
                    throw new RuntimeException(e);
                }
                sink.next(i);
            }
            sink.complete();
        })
                .delayElements(Duration.ofMillis(500))
                .doOnNext(value -> log.info("value: {}", value))
                .subscribeOn(Schedulers.single())
                .subscribe();

        Thread.sleep(6000);
    }
}
