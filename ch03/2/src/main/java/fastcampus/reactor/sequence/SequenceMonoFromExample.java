package fastcampus.reactor.sequence;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;

@Slf4j
public class SequenceMonoFromExample {

    public static void main(String[] args) {
        Mono.fromCallable(() -> {
            return 1;
        }).subscribe(value -> log.info("value from callable: {}", value));

        Mono.fromFuture(CompletableFuture.supplyAsync(() -> {
            return 1;
        })).subscribe(value -> log.info("value from future: {}", value));

        Mono.fromSupplier(() -> {
            return 1;
        }).subscribe(value -> log.info("value from supplier: {}", value));

        Mono.fromRunnable(() -> {

        }).subscribe(null, null, () -> log.info("complete fromRunnable"));
    }
}
