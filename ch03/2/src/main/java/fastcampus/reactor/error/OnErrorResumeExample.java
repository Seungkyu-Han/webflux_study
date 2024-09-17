package fastcampus.reactor.error;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;

import java.util.function.Function;

@Slf4j
public class OnErrorResumeExample {

    public static void main(String[] args) {
        log.info("start main");

        Flux.error(new RuntimeException("error"))
                .onErrorResume((Function<Throwable, Publisher<Integer>>) throwable -> Flux.just(0, 1, 2))
                .subscribe(value -> log.info("value: {}", value));

        log.info("end main");
    }
}
