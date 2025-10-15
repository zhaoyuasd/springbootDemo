package netty;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;

/**
 * @author dongli
 * @create 2024/2/22 13:54
 * @desc
 */

public class EchoHandler extends SimpleChannelUpstreamHandler {

    private String name;

    public EchoHandler(String name) {
        this.name = name;
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent event) throws Exception {
        try {
            ChannelBuffer m = (ChannelBuffer) event.getMessage();
            byte[] actual = new byte[m.readableBytes()];

            m.getBytes(0, actual);
            String raw =  new String(actual, 0, actual.length);
            System.out.println(name + " " + raw);
            String echo = name + "回写：" + raw;
            System.out.println("回写数据：" + echo);
            ctx.getChannel().write(ChannelBuffers.wrappedBuffer(echo.getBytes()));
            ctx.sendUpstream(event);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

