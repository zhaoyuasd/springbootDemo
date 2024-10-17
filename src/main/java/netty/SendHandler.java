package netty;

import com.alibaba.fastjson.JSON;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelDownstreamHandler;

/**
 * @author dongli
 * @create 2024/1/4 18:07
 * @desc
 */

public class SendHandler extends SimpleChannelDownstreamHandler {
    @Override
    public void writeRequested(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        System.out.println("send " + e.getMessage());
        ctx.sendDownstream(e);
    }
}
