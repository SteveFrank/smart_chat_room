package com.smart.im.client.handler;

import com.smart.im.common.bean.msg.ProtoMsg;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.springframework.stereotype.Service;

/**
 * 聊天消息处理的handler
 *
 * @author frankq
 * @date 2021/9/17
 */
@ChannelHandler.Sharable
@Service("chatMsgHandler")
public class ChatMsgHandler extends ChannelInboundHandlerAdapter {

    public ChatMsgHandler() {
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 判断消息实例
        if (!(msg instanceof ProtoMsg.Message)) {
            super.channelRead(ctx, msg);
            return;
        }
        // 判断类型
        ProtoMsg.Message pkg = (ProtoMsg.Message) msg;
        ProtoMsg.HeadType headType = pkg.getType();
        if (!headType.equals(ProtoMsg.HeadType.MESSAGE_REQUEST)) {
            super.channelRead(ctx, msg);
            return;
        }

        ProtoMsg.MessageRequest req = pkg.getMessageRequest();
        String content = req.getContent();
        String uid = req.getFrom();

        System.out.println(" 收到消息 from uid:" + uid + " -> " + content);
    }
}
