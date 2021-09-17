package com.smart.im.client.sender;

import com.smart.im.client.protoBuilder.LoginMsgBuilder;
import com.smart.im.common.bean.msg.ProtoMsg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author frankq
 * @date 2021/9/17
 */
@Slf4j
@Service("loginSender")
public class LoginSender extends BaseSender {

    public void sendLoginMsg() {
        if (!isConnected()) {
            log.info("还没有建立连接!");
            return;
        }

        log.info("构造登陆消息");
        // 构建好登陆消息后发往服务端
        ProtoMsg.Message message =
                LoginMsgBuilder.buildLoginMsg(getUser(), getSession());
        log.info("发送登录消息");

        super.sendMsg(message);

    }

}
