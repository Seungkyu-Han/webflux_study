package fastcampus.reactor.error;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
public class DoOnErrorExample {

    public static void main(String[] args) {
        log.info("start main");

        Flux.error(new RuntimeException("error"))
                .doOnError(error -> log.info("doOnError: {}", error))
                .subscribe(
                        value -> log.info("value: {}", value),
                        error -> log.error("error: {}", error.getMessage())
                );

        log.info("end main");
    }
}
