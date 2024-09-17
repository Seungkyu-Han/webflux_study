package fastcampus.reactor.subscribe;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.util.context.Context;

import java.util.List;

@Slf4j
public class SubscribeLambdaExample {

    public static void main(String[] args) {
        Flux.fromIterable(List.of(1, 2, 3, 4, 5))
                .subscribe(
                        value -> log.info("value: {}", value),
                        error -> log.error("error: {}", error.getMessage()),
                        () -> log.info("complete"), Context.empty());


    }
}
