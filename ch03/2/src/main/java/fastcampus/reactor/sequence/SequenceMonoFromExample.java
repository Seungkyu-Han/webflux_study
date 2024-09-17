package fastcampus.reactor.sequence;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;

@Slf4j
public class SequenceMonoFromExample {

    @SneakyThrows
    public static void main(String[] args) {
        Mono.fromCallable(() -> 1).subscribe(value -> log.info("value fromCallable: {}", value));

        Mono.fromFuture(
                CompletableFuture.supplyAsync(() -> {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    return 2;
                }))
                .subscribe(value -> log.info("value fromFuture: {}", value));

        Mono.fromSupplier(() -> 1)
                .subscribe(value -> log.info("value fromSupplier: {}", value));

        Mono.fromRunnable(() -> {

        }).subscribe(null, null, () -> log.info("complete fromRunnable"));

        Thread.sleep(2000);
    }
}
