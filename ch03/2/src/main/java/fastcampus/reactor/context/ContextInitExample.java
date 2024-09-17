package fastcampus.reactor.context;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.util.context.Context;

@Slf4j
public class ContextInitExample {

    public static void main(String[] args) {
        var initialContext = Context.of("name", "seungkyu init");


        Flux.just(1)
                .flatMap(v -> ContextLogger.logContext(v, "1"))
                .contextWrite(context ->
                        context.put("name", "seungkyu1"))
                .flatMap(v -> ContextLogger.logContext(v, "2"))
                .subscribe(null, null, null, initialContext);


    }
}
