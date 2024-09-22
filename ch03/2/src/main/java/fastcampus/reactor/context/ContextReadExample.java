package fastcampus.reactor.context;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.context.ContextView;

@Slf4j
public class ContextReadExample {

    @SneakyThrows
    public static void main(String[] args) {
        Flux.just(1)
                .flatMap(value -> {
                    return Mono.deferContextual(contextView -> {
                        String name = contextView.get("name");
                        log.info("name: {}", name);
                        return Mono.just(value);
                    });
                }).contextWrite(context -> context.put("name", "seungkyu"))
                .subscribe();


    }
}
