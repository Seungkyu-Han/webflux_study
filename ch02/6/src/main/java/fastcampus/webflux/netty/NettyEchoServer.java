package fastcampus.webflux.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.FutureListener;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NettyEchoServer {

    public static void main(String[] args) {
        EventLoopGroup parentGroup = new NioEventLoopGroup();
        EventLoopGroup childGroup = new NioEventLoopGroup(4);
        EventExecutorGroup eventExecutorGroup = new DefaultEventExecutorGroup(4);

        try{
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            var server = serverBootstrap
                    .group(parentGroup, childGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(
                                    eventExecutorGroup, new LoggingHandler(LogLevel.INFO)
                            );
                            socketChannel.pipeline().addLast(
                                    new StringEncoder(),
                                    new StringDecoder(),
                                    new NettyEchoServerHandler()
                            );
                        }
                    });

            server.bind(8080).sync()
                    .addListener(new FutureListener<>() {
                        @Override
                        public void operationComplete(Future<Void> future) throws Exception {
                            if (future.isSuccess()) {
                                log.info("Success to bind 8080");
                            } else {
                                log.error("Fail to bind 8080");
                            }
                        }
                    }).channel().closeFuture().sync();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally{
            parentGroup.shutdownGracefully();
            childGroup.shutdownGracefully();
        }
    }
}
