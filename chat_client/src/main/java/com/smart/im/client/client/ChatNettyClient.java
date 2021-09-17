package com.smart.im.client.client;

import com.smart.im.client.handler.ExceptionHandler;
import com.smart.im.client.handler.LoginResponseHandler;
import com.smart.im.client.sender.ChatSender;
import com.smart.im.client.sender.LoginSender;
import com.smart.im.common.bean.User;
import com.smart.im.common.codec.SimpleProtoBufDecoder;
import com.smart.im.common.codec.SimpleProtoBufEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.GenericFutureListener;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author frankq
 * @date 2021/9/17
 */
@Slf4j
@Data
@Service("chatNettyClient")
public class ChatNettyClient {

    /**
     * 服务器ip地址
     */
    @Value("${chat.server.ip}")
    private String host;
    /**
     * 服务器端口
     */
    @Value("${chat.server.port}")
    private int port;

    @Autowired
    private LoginResponseHandler loginResponseHandler;


    @Autowired
    private ExceptionHandler exceptionHandler;


    private Channel channel;
    private ChatSender sender;
    private LoginSender l;

    /**
     * 唯一标记
     */
    private boolean initFlag = true;
    private User user;
    private GenericFutureListener<ChannelFuture> connectedListener;

    private Bootstrap b;
    private EventLoopGroup g;

    public ChatNettyClient() {
        /*
         * 客户端的是Bootstrap，服务端的则是 ServerBootstrap。
         * 都是AbstractBootstrap的子类
         *
         * 通过nio方式来接收连接和处理连接
         */
        g = new NioEventLoopGroup();
    }

    /**
     * 重连
     */
    public void doConnect() {
        try {
            b = new Bootstrap();

            b.group(g);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
            b.remoteAddress(host, port);

            // 设置通道初始化
            b.handler(
                    new ChannelInitializer<SocketChannel>() {
                        public void initChannel(SocketChannel ch) {
                            ch.pipeline().addLast("decoder", new SimpleProtoBufDecoder());
                            ch.pipeline().addLast("encoder", new SimpleProtoBufEncoder());
                            ch.pipeline().addLast(loginResponseHandler);
                            ch.pipeline().addLast(exceptionHandler);
                        }
                    }
            );
            log.info("客户端开始连接 ... ...");
            //异步发起连接
            ChannelFuture f = b.connect();
            f.addListener(connectedListener);

            // 阻塞
            // f.channel().closeFuture().sync();

        } catch (Exception e) {
            log.info("客户端连接失败!" + e.getMessage());
        }
    }

    public void close() {
        g.shutdownGracefully();
    }


}
