package fastcampus.reactor.context;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.util.context.Context;

@Slf4j
public class ContextReadFromSinkExample {

    @SneakyThrows
    public static void main(String[] args) {
        var initialContext = Context.of("name", "king-seungkyu");

        Flux.create(
                sink -> {
                    var name = sink.contextView().get("name");
                    log.info("name in create: {}", name);
                    sink.next(1);
                }
        ).contextWrite(
                context -> context.put("name", "seungkyu")
        ).subscribe(null, null, null, initialContext);
    }
}
