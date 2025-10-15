package netty;

import io.netty.channel.DefaultEventLoop;
import io.netty.channel.local.LocalAddress;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.*;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;


/**
 * @author dongli
 * @create 2024/1/4 15:57
 * @desc
 */

public class NettyServer {
    public static void main(String[] args) throws Exception {
        ExecutorService boss =  new NioEventLoopGroup();
        ExecutorService work =  new NioEventLoopGroup();
        ChannelFactory channelFactory = new NioServerSocketChannelFactory(boss, work, 1);
        ServerBootstrap bootstrap  = new ServerBootstrap();
        bootstrap.setFactory(channelFactory);
        bootstrap.setOption("child.tcpNoDelay", true);

        /**
         * server 1 InternalDecoder test
         * server 2 InternalDecoder test
         * server_EchoHandler test
         * 回写数据：server_EchoHandler回写：test
         * server InternalEncoder server_EchoHandler回写：test
         * server_EchoHandler2222 test
         * 回写数据：server_EchoHandler2222回写：test
         * server InternalEncoder server_EchoHandler2222回写：test
         */
        bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            @Override
            public ChannelPipeline getPipeline() {
                ChannelPipeline pipeline = Channels.pipeline();
                pipeline.addLast("decoder", new InternalDecoder("server 1 InternalDecoder"));
                pipeline.addLast("encoder", new InternalEncoder("server InternalEncoder"));
                pipeline.addLast("handler", new InternalDecoder("server 2 InternalDecoder"));
                pipeline.addLast("echo", new EchoHandler("server_EchoHandler"));
               // pipeline.addLast("echo2", new EchoHandler("server_EchoHandler2222"));
                pipeline.addLast("ResponseHandler1", new ResponseHandler("ResponseHandler1"));
                pipeline.addLast("ResponseHandler2", new ResponseHandler("ResponseHandler2"));
                return pipeline;
            }
        });

        // server 端 接收请求属于 入站事件  在 pipeline 中 从顶部到底部
        // server 端 响应请求属于 出站事件  在 pipeline 中 从底部到顶部
        Channel channel = bootstrap.bind(new InetSocketAddress(InetAddress.getByAddress(new byte[] { 127, 0, 0, 1 }), 8888));
        InetSocketAddress address =  ((InetSocketAddress) channel.getLocalAddress());
        if (channel.isOpen()) {
            System.out.println("open " + address.getPort());
            System.out.println("open " + address.getHostString());
        }
    }
}
