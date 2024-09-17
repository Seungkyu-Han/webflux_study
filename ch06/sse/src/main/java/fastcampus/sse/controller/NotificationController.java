package fastcampus.sse.controller;

import fastcampus.sse.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private static Sinks.Many<String> sink = Sinks.many().multicast().onBackpressureBuffer();
    private static AtomicInteger lastEventId = new AtomicInteger(1);
    private final NotificationService notificationService;

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<String>> getNotifications() {
        return notificationService.getMessageFromSink()
                .map(message -> {
                    String id = lastEventId.getAndIncrement() + "";
                    return ServerSentEvent
                            .builder(message)
                            .event("notification")
                            .id(id)
                            .comment("this is notification")
                            .build();
                });
    }

    @PostMapping
    public Mono<String> addNotification(@RequestBody Event event) {
        String notificationMessage = event.getType() + ": " + event.getMessage();
        notificationService.tryEmitNext(notificationMessage);
        return Mono.just("OK");
    }
}
