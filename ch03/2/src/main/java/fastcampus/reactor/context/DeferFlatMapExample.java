package fastcampus.reactor.context;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
public class DeferFlatMapExample {

    public static void main(String[] args) {
        Mono.just(1)
                .flatMap(v -> Mono.defer(() -> {
                    return Mono.just(v);
                })).subscribe(n -> log.info("{}", n));
    }
}
