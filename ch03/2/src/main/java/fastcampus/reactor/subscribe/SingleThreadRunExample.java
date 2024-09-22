package fastcampus.reactor.subscribe;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.util.concurrent.Executors;

@Slf4j
public class SingleThreadRunExample {

    public static void main(String[] args) {
        var executor = Executors.newSingleThreadExecutor();

        try{
            executor.submit(() -> {
                Flux.create(sink -> {
                    for(int i = 0; i < 5; i++){
                        log.info("next: {}", i);
                        sink.next(i);
                    }
                }).subscribe(value -> log.info("value: {}", value));
            });
        }finally {
            executor.shutdown();
        }
    }
}
