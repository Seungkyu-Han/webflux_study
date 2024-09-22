package fastcampus.notification.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Service
@Slf4j
public class NotificationService {

    private static Sinks.Many<String> sink = Sinks.many().unicast().onBackpressureBuffer();

    public Flux<String> getMessageFromSink(){
        return sink.asFlux();
    }

    public void tryEmitNext(String message) {
        log.info("message: {}", message);
        
        sink.tryEmitNext(message);
    }
}
