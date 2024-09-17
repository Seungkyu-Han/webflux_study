package fastcampus.webflux.practice.eventloop.nettyServer;

import io.netty.channel.*;
import io.netty.handler.codec.http.FullHttpRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NettyEchoServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(msg instanceof String) {
            ctx.writeAndFlush(msg)
                    .addListener(ChannelFutureListener.CLOSE);
        }
    }
}
