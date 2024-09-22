package fastcampus.reactor.sequence;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.util.stream.IntStream;

@Slf4j
public class SequenceHandlerExample {

    public static void main(String[] args) {
        Flux.fromStream(IntStream.range(1, 10).boxed())
                .handle((value, sink) -> {
                    if(value % 2 == 0) {
                        sink.next(value);
                    }
                }).subscribe(
                        value -> log.info("value: {}", value),
                        error -> log.error("error: {}", error.getMessage()),
                        () -> log.info("complete"));
    }
}
