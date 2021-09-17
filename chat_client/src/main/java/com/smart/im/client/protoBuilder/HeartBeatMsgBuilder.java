package com.smart.im.client.protoBuilder;


import com.smart.im.client.client.ClientSession;
import com.smart.im.common.bean.User;
import com.smart.im.common.bean.msg.ProtoMsg;
import lombok.extern.slf4j.Slf4j;

/**
 * 心跳消息Builder
 */
@Slf4j
public class HeartBeatMsgBuilder extends BaseBuilder {

    private final User user;

    public HeartBeatMsgBuilder(User user, ClientSession session) {
        super(ProtoMsg.HeadType.HEART_BEAT, session);
        this.user = user;
    }

    public ProtoMsg.Message buildMsg() {
        ProtoMsg.Message message = buildCommon(-1);
        ProtoMsg.MessageHeartBeat.Builder lb =
                ProtoMsg.MessageHeartBeat.newBuilder()
                        .setSeq(0)
                        .setJson("{\"from\":\"client\"}")
                        .setUid(user.getUid());
        return message.toBuilder().setHeartBeat(lb).build();
    }


}


