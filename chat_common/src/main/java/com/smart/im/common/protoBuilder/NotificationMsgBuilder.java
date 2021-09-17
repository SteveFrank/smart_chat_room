package com.smart.im.common.protoBuilder;

import com.smart.im.common.bean.msg.ProtoMsg;

/**
 * 消息通知
 * @author frankq
 * @date 2021/9/17
 */
public class NotificationMsgBuilder {

    public static ProtoMsg.Message buildNotification(String json) {
        // 设置消息类型
        ProtoMsg.Message.Builder mb = ProtoMsg.Message.newBuilder()
                .setType(ProtoMsg.HeadType.MESSAGE_NOTIFICATION);


        // 设置应答流水，与请求对应
        ProtoMsg.MessageNotification.Builder rb =
                ProtoMsg.MessageNotification.newBuilder()
                        .setJson(json);
        mb.setNotification(rb.build());
        return mb.build();
    }

}
