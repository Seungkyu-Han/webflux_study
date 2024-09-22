package fastcampus.reactor.sequence;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
public class SequenceErrorExample {

    public static void main(String[] args) {
        Mono.error(new RuntimeException("mono error"))
                .subscribe(null, error -> log.error("mono error: {}", error.getMessage()));

        Flux.error(new RuntimeException("flux error"))
                .subscribe(null, error -> log.error("flux error: {}", error.getMessage()));
    }
}
