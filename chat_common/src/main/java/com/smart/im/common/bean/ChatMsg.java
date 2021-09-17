package com.smart.im.common.bean;

import com.smart.im.common.bean.msg.ProtoMsg;
import lombok.Data;
import org.apache.commons.lang.StringUtils;

/**
 *
 * 消息实体类
 *
 * @author frankq
 * @date 2021/9/16
 */
@Data
public class ChatMsg {

    /**
     * 消息类型  1：纯文本  2：音频 3：视频 4：地理位置 5：其他
     */
    public enum MSGTYPE {
        TEXT,
        AUDIO,
        VIDEO,
        POS,
        OTHER;
    }

    public ChatMsg(User user) {
        if (null == user) {
            return;
        }
        this.user = user;
        this.setTime(System.currentTimeMillis());
        this.setFrom(user.getUid());
        this.setFromNick(user.getNickName());

    }

    private User user;

    private long msgId;
    private String from;
    private String to;
    private long time;
    private MSGTYPE msgType;
    private String content;
    /**
     * 多媒体地址
     */
    private String url;
    /**
     * 附加属性
     */
    private String property;
    /**
     * 发送者昵称
     */
    private String fromNick;
    /**
     * 附加的json串
     */
    private String json;


    public void fillMsg(ProtoMsg.MessageRequest.Builder cb) {
        if (msgId > 0) {
            cb.setMsgId(msgId);
        }
        if (StringUtils.isNotEmpty(from)) {
            cb.setFrom(from);
        }
        if (StringUtils.isNotEmpty(to)) {
            cb.setTo(to);
        }
        if (time > 0) {
            cb.setTime(time);
        }
        if (msgType != null) {
            cb.setMsgType(msgType.ordinal());
        }
        if (StringUtils.isNotEmpty(content)) {
            cb.setContent(content);
        }
        if (StringUtils.isNotEmpty(url)) {
            cb.setUrl(url);
        }
        if (StringUtils.isNotEmpty(property)) {
            cb.setProperty(property);
        }
        if (StringUtils.isNotEmpty(fromNick)) {
            cb.setFromNick(fromNick);
        }
        if (StringUtils.isNotEmpty(json)) {
            cb.setJson(json);
        }
    }


}
