package fastcampus.webflux.practice.eventloop;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

public class SampleChannelInboundHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(msg instanceof String){
            ctx.writeAndFlush("Hello, " + msg)
                    .addListener(ChannelFutureListener.CLOSE);
        }
        else if(msg instanceof ByteBuf){
            try{
                var buf = (ByteBuf) msg;
                var len = buf.readableBytes();
                var charset = StandardCharsets.UTF_8;
                var body = buf.readCharSequence(len, charset);
                ctx.fireChannelRead(body);
            }finally {
                ReferenceCountUtil.release(msg);
            }
        }
    }
}
