package fastcampus.webflux.practice.eventloop;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

@Slf4j
public class NettyEchoServer {

    public static void main(String[] args) {
        EventLoopGroup parentGroup = new NioEventLoopGroup();
        EventLoopGroup childGroup = new NioEventLoopGroup(4);

        NioSocketChannel serverSocketChannel = new NioSocketChannel();

        parentGroup.register(serverSocketChannel);
        serverSocketChannel.pipeline().addLast(acceptor(childGroup));

        serverSocketChannel.bind(new InetSocketAddress(8080))
                .addListener(future -> {
                    if (future.isSuccess()) {
                        log.info("Server started on port 8080");
                    }
                });
    }

    private static ChannelInboundHandler acceptor(EventLoopGroup childGroup){
        var executorGroup = new DefaultEventLoopGroup(4);
        return new ChannelInboundHandlerAdapter(){
            @Override
            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                log.info("Acceptor.channelRead");
                if(msg instanceof SocketChannel){
                    SocketChannel socketChannel = (SocketChannel) msg;
                    socketChannel.pipeline()
                            .addLast(executorGroup, new LoggingHandler(LogLevel.INFO));
                    socketChannel.pipeline()
                            .addLast(echoHandler());
                    childGroup.register(socketChannel);
                }
            }
        };
    }

    private static ChannelInboundHandler echoHandler(){

        return new ChannelInboundHandlerAdapter(){
            @Override
            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                if(msg instanceof ByteBuf){
                    try{
                        var buf = (ByteBuf) msg;
                        var len = buf.readableBytes();
                        var charset = StandardCharsets.UTF_8;
                        var body = buf.readCharSequence(len, charset);
                        log.info("EchoHandler.channelRead: {}", body);

                        buf.readerIndex(0);
                        var result = buf.copy();
                        ctx.writeAndFlush(result)
                                .addListener(ChannelFutureListener.CLOSE);
                    }finally {
                        ReferenceCountUtil.release(msg);
                    }
                }
            }
        };
    }
}
