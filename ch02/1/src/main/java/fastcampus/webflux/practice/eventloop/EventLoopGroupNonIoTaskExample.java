package fastcampus.webflux.practice.eventloop;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EventLoopGroupNonIoTaskExample {

    public static void main(String[] args) {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup(5);

        for (int i = 0; i < 20; i++) {
            final int index = i;
            eventLoopGroup.execute(() -> log.info("i: {}", index));
        }

        eventLoopGroup.shutdownGracefully();
        log.info("end main");
    }
}
