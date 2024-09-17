package fastcampus.webflux.practice.eventloop;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

@Slf4j
public class NettyShortEchoServer {

    @SneakyThrows
    public static void main(String[] args) {
        EventLoopGroup parentGroup = new NioEventLoopGroup();
        EventLoopGroup childGroup = new NioEventLoopGroup(4);

        var bootStrap = new ServerBootstrap();
        var executorGroup = new DefaultEventExecutorGroup(4);
        var stringEncoder = new StringEncoder();
        var stringDecoder = new StringDecoder();

        var bind = bootStrap
                .group(parentGroup, childGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(@NonNull SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline()
                                .addLast(executorGroup, new LoggingHandler(LogLevel.INFO))
                                .addLast(stringEncoder, stringDecoder, echoHandler());
                    }
                })
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .bind(8080);

        bind.sync().addListener(future -> {
            if (future.isSuccess()) {
                log.info("Server started on port 8080");
            }
        });
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
