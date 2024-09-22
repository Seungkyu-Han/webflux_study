package fastcampus.reactor.error;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
public class ErrorConsumerExample {

    public static void main(String[] args) {
        Flux.error(new RuntimeException("error"))
                .subscribe(
                        value -> log.info("value: {}", value),
                        error -> log.error("error: {}", error.getMessage()));
    }
}
