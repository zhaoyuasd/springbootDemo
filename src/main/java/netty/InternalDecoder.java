package netty;

import com.alibaba.fastjson.JSON;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;

/**
 * @author dongli
 * @create 2024/1/4 16:05
 * @desc
 */

public class InternalDecoder extends SimpleChannelUpstreamHandler {

    private String name;

    public InternalDecoder(String name) {
        this.name = name;
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent event) throws Exception {
        try {
            ChannelBuffer m = (ChannelBuffer) event.getMessage();
            byte[] actual = new byte[m.readableBytes()];

            m.getBytes(0, actual);
            System.out.println(name + " " + new String(actual, 0, actual.length));
            ctx.sendUpstream(event);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
