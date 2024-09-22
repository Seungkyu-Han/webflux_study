package fastcampus.websocket.handler;

import fastcampus.websocket.service.ChatService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
@RequiredArgsConstructor
public class ChatWebSocketHandler implements WebSocketHandler {

    private final ChatService chatService;

    private static Map<String, Sinks.Many<Chat>> chatSinkMap = new ConcurrentHashMap<>();

    @Override
    public Mono<Void> handle(WebSocketSession session) {

        String iam = session.getAttributes().get("iam").toString();

        Flux<Chat> chatFlux = chatService.register(iam);

        chatService.sendChat(iam, new Chat(iam + "님 채팅방에 오신 것을 환영합니다.", "system"));

        session.receive()
                .doOnNext(
                        webSocketMessage -> {
                            String payload = webSocketMessage.getPayloadAsText();


                            String[] splits = payload.split(":");

                            String to = splits[0].trim();
                            String message = splits[1].trim();

                            boolean result = chatService.sendChat(to, new Chat(message, iam));
                            if(!result){
                                chatService.sendChat(iam, new Chat("대화 상대가 없습니다.", "system"));
                            }
                        }
                ).subscribe();

        return session.send(chatFlux
                        .map(
                                chat -> session.textMessage(chat.getFrom() + ": " + chat.getMessage())
                        )
                )
                .doFinally(signalType -> {
                    chatSinkMap.remove(iam);
                    log.info("WebSocket 연결이 종료되었습니다. {}의 sink를 제거합니다.", iam);
                });
    }
}
