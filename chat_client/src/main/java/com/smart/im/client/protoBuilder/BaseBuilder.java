package com.smart.im.client.protoBuilder;

import com.smart.im.client.client.ClientSession;
import com.smart.im.common.bean.msg.ProtoMsg;

/**
 *
 * 基础消息构造builder
 *
 * @author frankq
 * @date 2021/9/17
 */
public class BaseBuilder {

    protected ProtoMsg.HeadType type;
    private long seqId;
    private ClientSession session;

    public BaseBuilder(ProtoMsg.HeadType type, ClientSession session) {
        this.type = type;
        this.session = session;
    }

    /**
     * 构建消息 基础部分
     */
    public ProtoMsg.Message buildCommon(long seqId) {
        this.seqId = seqId;

        ProtoMsg.Message.Builder mb =
                ProtoMsg.Message
                        .newBuilder()
                        .setType(type)
                        .setSessionId(session.getSessionId())
                        .setSequence(seqId);
        return mb.buildPartial();
    }

    /**
     * 构建消息 基础部分的 Builder
     */
    public ProtoMsg.Message.Builder baseBuilder(long seqId) {
        this.seqId = seqId;

        ProtoMsg.Message.Builder mb =
                ProtoMsg.Message
                        .newBuilder()
                        .setType(type)
                        .setSessionId(session.getSessionId())
                        .setSequence(seqId);
        return mb;
    }

}
