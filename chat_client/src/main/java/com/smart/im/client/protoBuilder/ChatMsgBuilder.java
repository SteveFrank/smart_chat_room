package com.smart.im.client.protoBuilder;

import com.smart.im.client.client.ClientSession;
import com.smart.im.common.bean.ChatMsg;
import com.smart.im.common.bean.User;
import com.smart.im.common.bean.msg.ProtoMsg;

/**
 * @author frankq
 * @date 2021/9/17
 */
public class ChatMsgBuilder extends BaseBuilder {

    private ChatMsg chatMsg;
    private User user;


    public ChatMsgBuilder(ChatMsg chatMsg, User user, ClientSession session) {
        super(ProtoMsg.HeadType.MESSAGE_REQUEST, session);
        this.chatMsg = chatMsg;
        this.user = user;

    }


    public ProtoMsg.Message build() {
        ProtoMsg.Message message = buildCommon(-1);
        ProtoMsg.MessageRequest.Builder cb
                = ProtoMsg.MessageRequest.newBuilder();

        chatMsg.fillMsg(cb);
        return message
                .toBuilder()
                .setMessageRequest(cb)
                .build();
    }

    public static ProtoMsg.Message buildChatMsg(
            ChatMsg chatMsg,
            User user,
            ClientSession session) {
        ChatMsgBuilder builder =
                new ChatMsgBuilder(chatMsg, user, session);
        return builder.build();

    }

}
