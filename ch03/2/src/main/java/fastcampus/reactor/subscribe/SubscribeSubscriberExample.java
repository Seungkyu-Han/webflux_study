package fastcampus.reactor.subscribe;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;

import java.util.List;

@Slf4j
public class SubscribeSubscriberExample {

    public static void main(String[] args) {
        Flux.fromIterable(List.of(1, 2, 3, 4, 5))
                .subscribe(new Subscriber<>() {
                    @Override
                    public void onSubscribe(Subscription subscription) {
                        subscription.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        log.info("value: {}", integer);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        log.error("error: {}", throwable.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        log.info("completed");
                    }
                });
    }
}
