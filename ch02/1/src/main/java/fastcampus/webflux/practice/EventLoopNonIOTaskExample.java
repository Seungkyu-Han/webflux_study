package fastcampus.webflux.practice;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EventLoopNonIOTaskExample {

    public static void main(String[] args) {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup(1);

        for(int i = 0; i < 10; i++){
            final int idx = i;
            eventLoopGroup.execute(() -> {
                log.info("i: {}", idx);
            });
        }

        eventLoopGroup.shutdownGracefully();
    }
}
