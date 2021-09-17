package com.smart.im.client.sender;

import com.smart.im.client.client.ClientSession;
import com.smart.im.common.bean.User;
import com.smart.im.common.bean.msg.ProtoMsg;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author frankq
 * @date 2021/9/17
 */
@Data
@Slf4j
public abstract class BaseSender {

    private User user;
    private ClientSession session;

    public boolean isConnected() {
        if (null == session) {
            log.info("session is null");
            return false;
        }
        return session.isConnected();
    }

    public boolean isLogin() {
        if (null == session) {
            log.info("session is null");
            return false;
        }
        return session.isLogin();
    }

    public void sendMsg(ProtoMsg.Message message) {
        if (null == getSession() || !isConnected()) {
            log.info("连接没有成功，请等待 ... ...");
            return;
        }
        Channel channel = getSession().getChannel();
        ChannelFuture future = channel.writeAndFlush(message);
        future.addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future) throws Exception {
                // 成功完成的回调函数
                if (future.isSuccess()) {
                    sendSuccess(message);
                } else {
                    sendSuccess(message);
                }
            }
        });
    }

    /**
     * 发送成功回调
     */
    protected void sendSuccess(ProtoMsg.Message message) {

        log.info("发送成功");

    }

    /**
     * 发送失败回调
     * @param message
     */
    protected void sendFailed(ProtoMsg.Message message) {

        log.info("发送失败");
    }

}
