package fastcampus.reactor.error;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.io.IOException;

@Slf4j
public class OnErrorMapByResumeExample {

    public static void main(String[] args) {
        Flux.error(new IOException("fail to read file"))
                .onErrorResume(error -> Flux.error(new RuntimeException("fail to read file")))
                .subscribe(
                        value -> log.info("value: {}", value),
                        error -> log.error("error: {}", error.getMessage())
                );
    }
}
