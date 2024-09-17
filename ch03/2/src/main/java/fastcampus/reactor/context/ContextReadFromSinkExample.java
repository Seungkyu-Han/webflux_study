package fastcampus.reactor.context;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.util.context.Context;

@Slf4j
public class ContextReadFromSinkExample {

    public static void main(String[] args) {
        var initialContext = Context.of("name", "seungkyu");

        Flux.create(sink -> {
            var name = sink.contextView().get("name");
            log.info("name: {}", name);
            sink.next(1);
        }).contextWrite(context -> context.put("name", "seungkyu1"))
                .subscribe(null, null, null, initialContext);
    }
}
