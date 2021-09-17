package com.smart.im.client.client;

import com.smart.im.common.bean.User;
import com.smart.im.common.bean.msg.ProtoMsg;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * 客户端聊天会话
 * 1、聊天会话同时保留了聊天的用户信息
 * 2、聊天回复页保留了用户的channel信息
 * @author frankq
 * @date 2021/9/17
 */
@Data
@Slf4j
public class ClientSession {

    public static final AttributeKey<ClientSession> SESSION_KEY
            = AttributeKey.valueOf("SESSION_KEY");

    /**
     * 用户实现客户端会话管理的核心
     */
    private Channel channel;
    private User user;

    /**
     * 保存登陆后的服务端sessionId
     */
    private String sessionId;

    private volatile boolean isConnected = false;
    private volatile boolean isLogin = false;

    /**
     * session中存储session的各个变量属性
     */
    private Map<String, Object> map = new HashMap<>(16);

    /**
     * 绑定通道信息
     */
    public ClientSession(Channel channel) {
        this.channel = channel;
        this.sessionId = String.valueOf(-1);
        channel.attr(ClientSession.SESSION_KEY).set(this);
    }

    /**
     * 登陆成功后，设置sessionId
     */
    public static void loginSuccess(ChannelHandlerContext ctx, ProtoMsg.Message pkg) {
        Channel channel = ctx.channel();
        ClientSession session = channel.attr(ClientSession.SESSION_KEY).get();
        session.setSessionId(pkg.getSessionId());
        session.setLogin(true);
        log.info("登陆成功");
    }

    /**
     * 获取channel
     */
    public static ClientSession getSession(ChannelHandlerContext ctx) {
        Channel channel = ctx.channel();
        ClientSession session = channel.attr(ClientSession.SESSION_KEY).get();
        return session;
    }

    public String getRemoteAddress() {
        return channel.remoteAddress().toString();
    }

    /**
     * 写protobuf 数据帧
     */
    public ChannelFuture writeAndFlush(Object pkg) {
        ChannelFuture f = channel.writeAndFlush(pkg);
        return f;
    }

    public void writeAndClose(Object pkg) {
        ChannelFuture future = channel.writeAndFlush(pkg);
        future.addListener(ChannelFutureListener.CLOSE);
    }

    /**
     * 关闭通道
     */
    public void close() {
        isConnected = false;
        ChannelFuture future = channel.close();
        future.addListener((ChannelFutureListener) channelFuture -> {
            if (channelFuture.isSuccess()) {
                log.error("连接顺利断开");
            }
        });
    }

}
