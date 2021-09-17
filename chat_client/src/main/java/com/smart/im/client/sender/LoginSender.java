package com.smart.im.client.sender;

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



    }

}
