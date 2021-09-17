package com.smart.im.client.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author frankq
 * @date 2021/9/17
 */
@Slf4j
@ChannelHandler.Sharable
@Service("loginResponseHandler")
public class LoginResponseHandler extends ChannelInboundHandlerAdapter {



    /**
     * 业务逻辑的实际处理
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
    }
}
