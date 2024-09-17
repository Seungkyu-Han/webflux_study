package fastcampus.websocket.config;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ChatWebSocketHandler implements WebSocketHandler {

    private static final Map<String, Sinks.Many<String>> chatSinkMap = new ConcurrentHashMap<>();

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        String iam = (String) session.getAttributes().get("iam");

        Sinks.Many<String> sink = Sinks.many().unicast().onBackpressureBuffer();

        chatSinkMap.put(iam, sink);

        sink.tryEmitNext("채팅방에 오신 것을 환영합니다.");

        session.receive()
                .doOnNext(webSocketMessage -> {
                    String payload = webSocketMessage.getPayloadAsText();

                    String[] split = payload.split(" ");
                    String to = split[0].trim();
                    String message = split[1].trim();
                    Sinks.Many<String> targetSink = chatSinkMap.get(to);
                    if(targetSink != null){
                        targetSink.tryEmitNext(message);
                    }
                }).subscribe();


        return session.send(sink.asFlux()
                .map(session::textMessage)
        );
    }
}
