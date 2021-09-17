package com.smart.im.client.sender;

import com.smart.im.client.protoBuilder.ChatMsgBuilder;
import com.smart.im.common.bean.ChatMsg;
import com.smart.im.common.bean.msg.ProtoMsg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author frankq
 * @date 2021/9/17
 */
@Slf4j
@Service("chatSender")
public class ChatSender extends BaseSender {

    public void sendChatMsg(String touid, String content) {
        log.info("发送消息 startConnectServer");
        ChatMsg chatMsg = new ChatMsg(getUser());
        chatMsg.setContent(content);
        chatMsg.setMsgType(ChatMsg.MSGTYPE.TEXT);
        chatMsg.setTo(touid);
        chatMsg.setMsgId(System.currentTimeMillis());
        ProtoMsg.Message message =
                ChatMsgBuilder.buildChatMsg(chatMsg, getUser(), getSession());

        super.sendMsg(message);
    }

    @Override
    protected void sendSuccess(ProtoMsg.Message message) {
        log.info("发送成功:" + message.getMessageRequest().getContent());
    }



    @Override
    protected void sendFailed(ProtoMsg.Message message) {
        log.info("发送失败:" + message.getMessageRequest().getContent());
    }

}
