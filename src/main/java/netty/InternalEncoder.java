package netty;

import com.alibaba.fastjson.JSON;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelDownstreamHandler;


/**
 * @author dongli
 * @create 2024/1/4 16:26
 * @desc
 */

public class InternalEncoder extends SimpleChannelDownstreamHandler {

    private String name;

    public  InternalEncoder(String name) {
        this.name = name;
    }
    @Override
    public void writeRequested(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        ChannelBuffer m = (ChannelBuffer) e.getMessage();
        byte[] actual = new byte[m.readableBytes()];
        m.getBytes(0, actual);
        System.out.println(name + " " + new String(actual, 0, actual.length));
        ctx.sendDownstream(e);
    }

    @Override
    public void connectRequested(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        System.out.println(name + " connectRequested");
        ctx.sendDownstream(e);
    }
}
