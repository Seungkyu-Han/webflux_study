package fastcampus.webhandler.practice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Slf4j
public class GreetingHandler {

    public static Mono<ServerResponse> greetQueryParam(ServerRequest serverRequest) {
        String name = serverRequest.queryParam("name")
                .orElse("world");

        String content = "Hello " + name;
        return ServerResponse.ok().bodyValue(content);
    }
}
