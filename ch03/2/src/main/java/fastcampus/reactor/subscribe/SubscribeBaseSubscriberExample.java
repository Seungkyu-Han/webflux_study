package fastcampus.reactor.subscribe;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;

import java.util.List;

@Slf4j
public class SubscribeBaseSubscriberExample {

    public static void main(String[] args) {
        var subscriber = new BaseSubscriber<Integer>(){
            @Override
            protected void hookOnNext(Integer value) {
                log.info("onNext: {}", value);
            }

            @Override
            protected void hookOnComplete() {
                log.info("onComplete");
            }
        };

        Flux.fromIterable(List.of(1, 2, 3, 4, 5))
                .subscribe(subscriber);

    }
}
