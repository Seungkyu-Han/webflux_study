package fastcampus.reactor.util;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
public class FilterExample {

    public static void main(String[] args) {
        Flux.range(1, 5)
                .filter(value -> value % 2 == 0)
                .doOnNext(System.out::println)
                .subscribe();
    }
}
