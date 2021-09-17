package com.smart.im.client.handler;

import com.smart.im.client.client.ClientSession;
import com.smart.im.client.client.CommandController;
import com.smart.im.common.exception.InvalidFrameException;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Slf4j
@ChannelHandler.Sharable
@Service("ExceptionHandler")
public class ExceptionHandler extends ChannelInboundHandlerAdapter {

    @Autowired
    private CommandController commandController;

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // ..

        if (cause instanceof InvalidFrameException) {
            log.error(cause.getMessage());
            ClientSession.getSession(ctx).close();
        } else {
            log.error(cause.getMessage());
            ctx.close();

            //开始重连
            commandController.setConnectFlag(false);
            commandController.startConnectServer();
        }
    }

    /**
     * 通道 Read 读取 Complete 完成
     * 做刷新操作 ctx.flush()
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

}