package netty;

import io.netty.channel.nio.NioEventLoopGroup;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.*;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author dongli
 * @create 2024/1/4 16:18
 * @desc
 */

public class NettyClient {
    public static void main(String[] args) throws Exception {
        ClientBootstrap bootstrap = new ClientBootstrap();
        bootstrap.setOption("keepAlive", true);
        bootstrap.setOption("tcpNoDelay", true);
        ExecutorService boss =  new NioEventLoopGroup();
        ExecutorService work =  new NioEventLoopGroup();
        ChannelFactory channelFactory = new NioClientSocketChannelFactory(boss, work);
        bootstrap.setFactory(channelFactory);

        /**
         *  client3 InternalEncoder connectRequested
         * client2 InternalEncoder connectRequested
         * SendHandler connectRequested
         * client3 InternalEncoder test
         * client2 InternalEncoder test
         * SendHandler test
         * write over
         * cilent InternalDecoder server_EchoHandler回写：testserver_EchoHandler2222回写：test
         * cilent2 InternalDecoder server_EchoHandler回写：testserver_EchoHandler2222回写：test
         */
        bootstrap.setPipelineFactory(() -> {
            ChannelPipeline pipeline = Channels.pipeline();
            pipeline.addLast("decoder", new InternalDecoder("cilent InternalDecoder"));
            pipeline.addLast("SendHandler", new SendHandler());
            pipeline.addLast("handler1", new InternalEncoder("client2 InternalEncoder"));
            pipeline.addLast("handler2", new InternalEncoder("client3 InternalEncoder"));
            pipeline.addLast("decoder2", new InternalDecoder("cilent2 InternalDecoder"));
            return pipeline;
        });
        //  client端 down事件 在 pipeline 中 由底部到顶部   client端的write属于 down事件  出站事件  连接也属于出站事件
        //           up事件   在 pipeline 中 由顶部到底部   server端响应数据    up事件    入站事件
        ChannelFuture ccf = bootstrap.connect(new InetSocketAddress("127.0.0.1", 8888));
        boolean ret = ccf.awaitUninterruptibly(2000, TimeUnit.MILLISECONDS);
        if (!ret || !ccf.isSuccess()) {
            System.out.println("connection fail");
        }
        Channel  channel = ccf.getChannel();
        if (channel.isOpen()) {
            channel.write(ChannelBuffers.wrappedBuffer("test".getBytes()));
            System.out.println("write over");
        } else {
            System.out.println("not open");
        }
    }
}
