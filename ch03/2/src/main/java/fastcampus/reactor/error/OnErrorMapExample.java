package fastcampus.reactor.error;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.io.IOException;

@Slf4j
public class OnErrorMapExample {
    public static void main(String[] args) {
        log.info("start main");

        Flux.error(new IOException("fail to read file"))
                .onErrorMap(error -> new NullPointerException("custom"))
                .subscribe(
                        value -> log.info("value: {}", value),
                        error -> log.info("error: {}", error.getMessage()));

        log.info("end main");
    }
}
