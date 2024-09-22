package fastcampus.webflux.practice;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.ResponseCookie;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import reactor.core.publisher.Mono;
import reactor.netty.http.server.HttpServer;

@Slf4j
public class HttpHandlerExample {

    @SneakyThrows
    public static void main(String[] args) {
        var handler = new HttpHandler(){
            @Override
            public Mono<Void> handle(
                    ServerHttpRequest request,
                    ServerHttpResponse response) {
                String nameQuery = request.getQueryParams().getFirst("name");
                String name = (nameQuery == null) ? "world" : nameQuery;

                String content = "Hello " + name;

                Mono<DataBuffer> responseBody = Mono.just(
                        response.bufferFactory().wrap(content.getBytes())
                );

                response.addCookie(ResponseCookie.from("name", name).build());
                response.getHeaders().add("Content-Type", "text/plain");

                return response.writeWith(responseBody);
            }
        };


        var adapter = new ReactorHttpHandlerAdapter(handler);
        HttpServer.create()
                .host("localhost")
                .port(8080)
                .handle(adapter)
                .bindNow()
                .channel().closeFuture().sync();
    }

}
