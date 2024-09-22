package fastcampus.reactor.error;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
public class ErrorNoHandlerExample {

    @SneakyThrows
    public static void main(String[] args) {
        Flux.create(sink -> {
            try{
                Thread.sleep(1000);
            }catch(InterruptedException e){
                throw new RuntimeException(e);
            }
            sink.error(new RuntimeException("error"));
        }).subscribe();
    }
}
