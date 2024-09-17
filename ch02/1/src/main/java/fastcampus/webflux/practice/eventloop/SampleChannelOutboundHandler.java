package fastcampus.webflux.practice.eventloop;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

import java.nio.charset.StandardCharsets;

public class SampleChannelOutboundHandler extends ChannelOutboundHandlerAdapter {

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        if(msg instanceof String) {
            ctx.write(msg, promise);
        }
        else if(msg instanceof byte[]) {
            var buf = (ByteBuf) msg;
            var len = buf.readableBytes();
            var charset = StandardCharsets.UTF_8;
            var body = buf.readCharSequence(len, charset);
            ctx.write(body, promise);
        }
    }
}
