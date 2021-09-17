package com.smart.im.common.codec;

import com.google.protobuf.InvalidProtocolBufferException;
import com.smart.im.common.bean.msg.ProtoMsg;
import com.smart.im.common.exception.InvalidFrameException;
import com.smart.im.common.ProtoInstant;
import com.smart.util.Logger;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * 通用消息解码器
 *
 * @author frankq
 * @date 2021/9/16
 */
public class SimpleProtoBufDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out)
            throws Exception {
        Object outmsg = decode0(ctx, in);
        if (outmsg != null) {
            // 获取业务消息
            out.add(outmsg);
        }
    }

    public static Object decode0(ChannelHandlerContext ctx, ByteBuf in)
            throws InvalidFrameException, InvalidProtocolBufferException {
        // 标记当前的readIndex位置
        in.markReaderIndex();
        // 判断包的头长度
        if (in.readableBytes() < 8) {
            // 不够包头的长度直接返回null，不予后续处理
            return null;
        }
        // 读取魔数判断是否口令正确
        short magic = in.readShort();
        if (magic != ProtoInstant.MAGIC_CODE) {
            String error = "客户端口令不正确, 地址 : " + ctx.channel().remoteAddress();
            throw new InvalidFrameException(error);
        }
        // 读取客户端版本信息
        short version = in.readShort();
        // 读取传输来的消息长度
        int length = in.readInt();

        // 长度如果小于0
        if (length < 0) {
            // 非法数据，直接关闭连接
            ctx.close();
        }

        // 如果读的消息体长度小于传送过来的消息长度
        if (length > in.readableBytes()) {
            // 重复读取位置
            in.resetReaderIndex();
            return null;
        }
        Logger.cfo("decoder length = " + in.readableBytes());

        byte[] array;
        if (in.hasArray()) {
            array = new byte[length];
            in.readBytes(array, 0, length);
        } else {
            // 直接缓冲
            array = new byte[length];
            in.readBytes(array, 0, length);
        }

        // 字节转对象
        ProtoMsg.Message outMsg = ProtoMsg.Message.parseFrom(array);

        return outMsg;
    }

}
