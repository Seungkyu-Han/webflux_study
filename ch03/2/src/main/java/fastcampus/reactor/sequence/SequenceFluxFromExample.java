package fastcampus.reactor.sequence;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.stream.IntStream;

@Slf4j
public class SequenceFluxFromExample {

    public static void main(String[] args) {

        log.info("start main");

        Flux.fromIterable(
                List.of(1, 2, 3, 4, 5)
        ).subscribe(SequenceFluxFromExample::logValue);

        Flux.fromStream(
                IntStream.range(1, 6).boxed()
        ).subscribe(SequenceFluxFromExample::logValue);

        Flux.fromArray(
                new Integer[]{1, 2, 3, 4, 5}
        ).subscribe(SequenceFluxFromExample::logValue);

        Flux.range(1, 5)
                .subscribe(SequenceFluxFromExample::logValue);

        log.info("end main");
    }

    private static void logValue(Object value){
        log.info(value.toString());
    }
}
