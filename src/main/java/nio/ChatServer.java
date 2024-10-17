package nio;

/**
 * @author dongli
 * @create 2024/10/17 13:45
 * @desc
 */

import cn.hutool.core.collection.CollectionUtil;
import common.BufferMessage;
import common.CommonUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class ChatServer {
    public static void main(String[] args) {
        Selector selector = null;
        ServerSocketChannel serverSocketChannel = null;
        try {
            selector = Selector.open();
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.socket().bind(new InetSocketAddress(19999));
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            while (true) {
                if (selector.select(500) == 0) {
                    continue;
                }
                if (CollectionUtil.isEmpty(selector.selectedKeys())) {
                    System.out.println(" no selectedKeys ");
                    Thread.sleep(3_000);
                    continue;
                }
                Iterator<SelectionKey> it = selector.selectedKeys().iterator();
                while (it.hasNext()) {
                    SelectionKey key = it.next();
                    it.remove();
                    if (!key.isValid()) {
                        System.out.println(System.nanoTime() + "   close");
                        continue;
                    }
                    /**
                     *  第一次触发的是 isAcceptable 而且这个时候一定是 ServerSocketChannel
                     */
                    if (key.isAcceptable()) {
                        System.out.println("isAcceptable");
                        handleAccept(key);
                    }
                    if (key.isWritable()) {
                        System.out.println("isWritable");
                        handleWrite(key);
                        key.interestOps(key.interestOps() & ~SelectionKey.OP_WRITE);
                        // key.selector().wakeup();
                    }
                    if (key.isReadable()) {
                        System.out.println("isReadable");
                        handleRead(key);
                        // HandleRead.handleRead(key);
                    }
                }
                selector.selectedKeys().clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void handleWrite(SelectionKey key) {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        ByteBuffer byteBuffer = (ByteBuffer) key.attachment();
        if (byteBuffer != null) {
            System.out.println("not null");
            String str = CommonUtil.decode(byteBuffer);
            System.out.println(str);
        }
        byteBuffer = ByteBuffer.allocate(1024);
        byteBuffer.put(CommonUtil.endByte());
        byteBuffer.flip();
        while (byteBuffer.hasRemaining()) {
            try {
                System.out.println("send back");
                socketChannel.write(byteBuffer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private static void handleRead(SelectionKey key) {
        SocketChannel socketChannel = (SocketChannel) key.channel();

        BufferMessage msg = (BufferMessage) key.attachment();
        if (msg == null) {
            msg = BufferMessage.init();
        }
        ByteBuffer byteBuffer = msg.getByteBuffer();
      /*  ByteBuffer byteBuffer = (ByteBuffer) key.attachment();
        if(byteBuffer==null){
            byteBuffer=  ByteBuffer.allocate(1024);
        }*/
        boolean isFinish = false;
        try {
            int length;
            boolean goon = true;
            /** 读取头部整数后 有没有将后续的字符串一并读出来 没有就是false**/

            while (goon) {
                length = socketChannel.read(byteBuffer);
                // 这里如果拿到是0 后续read也还是0 需要重新注册上去
                if (length == 0) {
                    goon = false;
                    continue;
                }
                Thread.sleep(400);
                //Thread.sleep(1*1000);
                //System.out.println(CommonUtil.decode(byteBuffer));
                //从socket填充完毕 转为读取状态
                System.out.println("length:" + length + " currentDeal:" + msg.isCurrentDeal());
                byteBuffer.flip();
                if (!msg.isCurrentDeal() && msg.getInfoLength() > 0) {
                    int remain = byteBuffer.remaining();
                    if (byteBuffer.remaining() >= msg.getInfoLength()) {
                        byte[] info = new byte[msg.getInfoLength()];
                        byteBuffer.get(info);
                        System.out.println("unDeal:" + new String(info));
                        msg.setCurrentDeal(true);
                    } else {
                        System.out.println("!currentDeal&&infoLength>0 continue");
                        continue;
                    }
                }

                // 1. 首先读取头部整数
                if (byteBuffer.remaining() >= 4) {
                    msg.setInfoLength(byteBuffer.getInt());
                    msg.setCurrentDeal(false);
                } else {
                    System.out.println("首先读取头部整数 position:" + byteBuffer.position() + " limit:" + byteBuffer.limit());
                    Thread.sleep(1 * 1000);
                    continue;

                }
                System.out.println("infoLength:" + msg.getInfoLength());
                //2. 进入循环 每次验证剩余的是否够一个包 不够则进行读取
                while (byteBuffer.remaining() >= msg.getInfoLength()) {
                    byte[] info = new byte[msg.getInfoLength()];
                    byteBuffer.get(info);
                    String infoStr = new String(info);
                    System.out.println(infoStr);
                    //  标记处理完毕
                    msg.setCurrentDeal(true);
                    // 判断是否发送过来结束符
                    if (infoStr.equalsIgnoreCase(CommonUtil.END)) {
                        System.out.println(" receive over go out");
                        goon = false;
                        isFinish = true;
                        break;
                    }
                    if (byteBuffer.remaining() >= 4) {
                        // 标记未处理完毕 进入下一轮循环
                        msg.setInfoLength(byteBuffer.getInt());
                        msg.setCurrentDeal(false);
                        System.out.println("currentDeal=false;  break");
                    } else {
                        System.out.println("break");
                        break;
                    }
                }
                // 将没有读取的部分 压到头部 继续进行填充
                byteBuffer.compact();
            }

            if (isFinish) {
                byte[] b = CommonUtil.endByte();
                ByteBuffer buf = ByteBuffer.allocate(b.length);
                buf.put(b);
                buf.flip();
                ((SocketChannel) key.channel()).write(buf);
                key.channel().close();
            } else {
                key.channel().register(key.selector(), SelectionKey.OP_READ, msg);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void handleAccept(SelectionKey key) {
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
        SocketChannel sc = null;
        try {
            sc = serverSocketChannel.accept();
            System.out.println(sc == null);
            if (sc != null) {
                sc.configureBlocking(false);
                sc.register(key.selector(), SelectionKey.OP_READ);
               /* byte[] m="a lo ha==".getBytes();
                ByteBuffer buf=ByteBuffer.allocate(m.length);
                sc.write(buf);*/
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
