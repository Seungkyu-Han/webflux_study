package fastcampus.reactor.context;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
public class DeferFlatMapExample {

    @SneakyThrows
    public static void main(String[] args) {
        Mono.just(1)
                .flatMap(v -> Mono.defer(() -> {
                    return Mono.just(1);
                })).subscribe( n -> {
                    log.info("next: {}", n);
                });

        Thread.sleep(1000);
    }
}
