package com.smart.im.common.codec;

import com.smart.im.common.bean.msg.ProtoMsg;
import com.smart.im.common.protoBuilder.ProtoInstant;
import com.smart.util.Logger;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

/**
 * 通用消息编码器
 *
 * @author frankq
 * @date 2021/9/16
 */
@Slf4j
public class SimpleProtoBufEncoder extends MessageToByteEncoder<ProtoMsg.Message> {

    @Override
    protected void encode(ChannelHandlerContext ctx, ProtoMsg.Message msg, ByteBuf out)
            throws Exception {
        encode0(msg, out);
    }

    public static void encode0(ProtoMsg.Message msg, ByteBuf out) {
        // 魔数用于校验
        out.writeShort(ProtoInstant.MAGIC_CODE);
        // 版本信息
        out.writeShort(ProtoInstant.VERSION_CODE);
        // 将消息对象转换为byte数组
        byte[] bytes = msg.toByteArray();

        // TODO 此处可以增加加密信息

        // 获取字节数组长度
        int length = bytes.length;

        Logger.cfo("encoder length = " + length);

        // 消息头：写入消息长度
        out.writeInt(length);
        // 消息体中需要包含我们发送的数据
        out.writeBytes(bytes);
    }

}
