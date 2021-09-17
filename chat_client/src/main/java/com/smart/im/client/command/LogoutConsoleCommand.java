package com.smart.im.client.command;

import com.smart.util.Logger;
import org.springframework.stereotype.Service;

import java.util.Scanner;

/**
 * @author frankq
 * @date 2021/9/17
 */
@Service("logoutConsoleCommand")
public class LogoutConsoleCommand implements BaseCommand {

    public static final String KEY = "10";

    @Override
    public void exec(Scanner scanner) {
        Logger.cfo("退出命令执行成功");
    }

    @Override
    public String getKey() {
        return KEY;
    }

    @Override
    public String getTip() {
        return "退出";
    }
}
