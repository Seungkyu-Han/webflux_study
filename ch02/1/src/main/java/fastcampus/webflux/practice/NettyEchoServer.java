package fastcampus.webflux.practice;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NettyEchoServer {

    @SneakyThrows
    public static void main(String[] args) {
        EventLoopGroup parentGroup = new NioEventLoopGroup();
        EventLoopGroup childGroup = new NioEventLoopGroup(4);
        EventExecutorGroup eventExecutorGroup = new DefaultEventExecutorGroup(4);

        try{
            ServerBootstrap bootstrap = new ServerBootstrap();
            var server = bootstrap
                    .group(parentGroup, childGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<>() {
                        @Override
                        protected void initChannel(Channel channel) throws Exception {
                            channel.pipeline()
                                    .addLast(eventExecutorGroup, new LoggingHandler(LogLevel.INFO));
                            channel.pipeline()
                                    .addLast(
                                            new StringEncoder(),
                                            new StringDecoder(),
                                            new NettyEchoServerHandler()
                                    );
                        }
                    });

            server.bind(8080)
                    .addListener((ChannelFutureListener) channelFuture -> {
                        if(channelFuture.isSuccess())
                            log.info("Success");
                        else
                            log.info("Error");
                    })
                    .channel().closeFuture().sync();

        }finally {
            parentGroup.shutdownGracefully();
            childGroup.shutdownGracefully();
            eventExecutorGroup.shutdownGracefully();
        }
    }
}
