package fastcampus.reactor.util;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
public class CollectListExample {

    public static void main(String[] args) {
        Flux.range(1, 5)
                .collectList()
                .doOnNext(System.out::println)
                .subscribe();
    }
}
