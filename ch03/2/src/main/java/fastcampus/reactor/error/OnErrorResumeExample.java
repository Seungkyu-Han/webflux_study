package fastcampus.reactor.error;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;

import java.util.function.Function;

@Slf4j
public class OnErrorResumeExample {

    @SneakyThrows
    public static void main(String[] args) {

        Flux.error(new RuntimeException("error"))
                .onErrorResume(new Function<Throwable, Publisher<Integer>>() {
                    @Override
                    public Publisher<Integer> apply(Throwable throwable) {
                        return Flux.just(1, 2, 3);
                    }
                })
                .subscribe(
                        value -> log.info("value: {}", value)
                );
    }
}
