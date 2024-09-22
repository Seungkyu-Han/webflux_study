package fastcampus.reactor.context;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
public class ContextWriteExample {

    @SneakyThrows
    public static void main(String[] args) {
        Flux.just(1)
                .flatMap(
                        v -> ContextLogger.logContext(v, "1")
                )
                .contextWrite(
                        context ->
                                context.put("name", "king-seungkyu")
                )
                .flatMap(
                        v -> ContextLogger.logContext(v, "2")
                ).contextWrite(
                        context ->
                                context.put("name", "new-seungkyu")
                )
                .flatMap(v -> ContextLogger.logContext(v, "3"))
                .subscribe();
    }
}
