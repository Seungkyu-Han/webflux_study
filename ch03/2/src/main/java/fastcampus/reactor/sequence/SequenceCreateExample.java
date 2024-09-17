package fastcampus.reactor.sequence;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.util.concurrent.CompletableFuture;

@Slf4j
public class SequenceCreateExample {

    @SneakyThrows
    public static void main(String[] args) {

        Flux.create(sink -> {
            var task1 = CompletableFuture.runAsync(() -> {
                for (int i = 0; i < 5; i++) {
                    sink.next(i);
                }
            });

            var task2 = CompletableFuture.runAsync(() -> {
                for (int i = 0; i < 5; i++) {
                    sink.next(i);
                }
            });

            CompletableFuture.allOf(task1, task2)
                    .thenRun(sink::complete);
        }).subscribe(
                value -> log.info("value: {}", value),
                error -> log.error("value: {}", error.getMessage()),
                () -> log.info("complete"));

        Thread.sleep(1000);
    }
}
