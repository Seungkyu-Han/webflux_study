package fastcampus.reactor.sequence;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
public class SequenceEmptyExample {

    public static void main(String[] args) {

        Mono.empty()
                .subscribe(null, null, () -> log.info("mono empty"));

        Flux.empty()
                .subscribe(null, null, () -> log.info("flux empty"));
    }
}
