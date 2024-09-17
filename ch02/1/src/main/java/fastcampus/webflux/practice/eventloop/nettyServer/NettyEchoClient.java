package fastcampus.webflux.practice.eventloop.nettyServer;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LoggingHandler;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;

@Slf4j
public class NettyEchoClient {

    @SneakyThrows
    public static void main(String[] args) {
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try{
            Bootstrap bootStrap = new Bootstrap();

            var client = bootStrap
                    .group(workerGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<Channel>() {
                        @Override
                        protected void initChannel(@NonNull Channel channel) throws Exception {
                            channel
                                    .pipeline().addLast(
                                            new LoggingHandler(),
                                            new StringEncoder(),
                                            new StringDecoder(),
                                            new NettyEchoClientHandler()
                                    );
                        }
                    });

            client.connect("127.0.0.1", 8080).sync()
                    .channel().closeFuture().sync();
        }finally {
            workerGroup.shutdownGracefully();
        }
    }
}
