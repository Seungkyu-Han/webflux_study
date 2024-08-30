package fastcampus.webflux.practice;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LoggingHandler;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NettyEchoClient {

    @SneakyThrows
    public static void main(String[] args) {
        EventLoopGroup group = new NioEventLoopGroup(1);

        try{
            Bootstrap bootstrap = new Bootstrap();
            var client = bootstrap
                    .group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<Channel>() {
                        @Override
                        protected void initChannel(Channel channel) throws Exception {
                            channel.pipeline().addLast(
                                    new LoggingHandler(),
                                    new StringEncoder(),
                                    new StringDecoder(),
                                    new NettyEchoClientHandler()
                            );
                        }
                    });

            client.connect("localhost", 8080)
                    .sync()
                    .channel().closeFuture().sync();

        } finally {
            group.shutdownGracefully();
        }
    }
}
