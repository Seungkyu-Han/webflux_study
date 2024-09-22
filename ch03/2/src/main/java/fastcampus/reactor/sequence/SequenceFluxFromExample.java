package fastcampus.reactor.sequence;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.stream.IntStream;

@Slf4j
public class SequenceFluxFromExample {

    public static void main(String[] args) {
        Flux.fromIterable(List.of(1, 2, 3, 4, 5))
                .subscribe(value -> log.info("iterable value: {}", value));

        Flux.fromStream(IntStream.range(1, 6).boxed())
                .subscribe(value -> log.info("stream value: {}", value));

        Flux.fromArray(
                new Integer[]{1, 2, 3, 4, 5}
        ).subscribe(value -> log.info("array value: {}", value));

        Flux.range(1, 5)
                .subscribe(value -> log.info("range value: {}", value));
    }
}
