package fastcampus.reactor.context;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class UselessThreadLocalExample {

    public static void main(String[] args) throws InterruptedException {
        ThreadLocal<String> threadLocal = new ThreadLocal<>();

        threadLocal.set("seungkyu");

        Flux.create(sink -> {
            log.info("threadLocal1: {}", threadLocal.get());
            sink.next(1);
        }).publishOn(Schedulers.parallel())
                .map(value -> {
                    log.info("threadLocal2: {}", threadLocal.get());
                    return value;
                }).publishOn(Schedulers.boundedElastic())
                .map(
                        value -> {
                            log.info("threadLocal3: {}", threadLocal.get());
                            return value;
                        })
                .subscribeOn(Schedulers.single())
                .subscribe();

        Thread.sleep(1000);
    }
}
