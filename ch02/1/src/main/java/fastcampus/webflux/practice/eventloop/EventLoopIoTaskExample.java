package fastcampus.webflux.practice.eventloop;

import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

@Slf4j
public class EventLoopIoTaskExample {

    public static void main(String[] args) {
        log.info("start main");

        var channel = new NioServerSocketChannel();
        var eventLoopGroup = new NioEventLoopGroup(1);

        eventLoopGroup.register(channel);

        channel.bind(new InetSocketAddress(8080))
                .addListener(future -> {
                    if (future.isSuccess()) {
                        log.info("Server bound to port 8080");
                    }
                    else{
                        log.info("Server bound to error 8080");
                    }
                });

        log.info("end main");
    }
}
