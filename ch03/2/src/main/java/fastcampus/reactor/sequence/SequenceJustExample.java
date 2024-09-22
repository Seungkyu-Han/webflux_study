package fastcampus.reactor.sequence;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
public class SequenceJustExample {

    public static void main(String[] args) {

        Mono.just(1)
                .subscribe(value -> log.info("{}", value));

        Flux.just(1, 2, 3, 4, 5).subscribe(value -> log.info("{}", value));
    }
}
