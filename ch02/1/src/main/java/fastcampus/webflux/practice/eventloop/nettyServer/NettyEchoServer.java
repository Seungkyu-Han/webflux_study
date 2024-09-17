package fastcampus.webflux.practice.eventloop.nettyServer;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.*;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;

@Slf4j
public class NettyEchoServer {

    @SneakyThrows
    public static void main(String[] args){

        EventLoopGroup parentGroup = new NioEventLoopGroup();
        EventLoopGroup childGroup = new NioEventLoopGroup(4);
        EventExecutorGroup eventExecutorGroup = new DefaultEventExecutorGroup(4);

        try{
            ServerBootstrap serverBootStrap = new ServerBootstrap();
            var server = serverBootStrap
                    .group(parentGroup, childGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(@NonNull SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline()
                                    .addLast(eventExecutorGroup, new LoggingHandler(LogLevel.INFO));
                            socketChannel.pipeline()
                                    .addLast(
                                            new StringEncoder(),
                                            new StringDecoder(),
                                            new NettyEchoServerHandler());
                        }
                    });

            server.bind(8080)
                    .addListener((ChannelFutureListener) channelFuture -> {
                        if(channelFuture.isSuccess()){
                            log.info("success");
                        }
                        else{
                            log.error("fail");
                        }
                    }).channel().closeFuture().sync();


        }finally {
            parentGroup.shutdownGracefully();
            childGroup.shutdownGracefully();
        }
    }
}
