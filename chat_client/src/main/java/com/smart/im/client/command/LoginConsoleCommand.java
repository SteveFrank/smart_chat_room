package com.smart.im.client.command;

import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.Scanner;

/**
 * 登陆命令行处理
 * @author frankq
 * @date 2021/9/17
 */
@Data
@Service("loginConsoleCommand")
public class LoginConsoleCommand implements BaseCommand {

    public static final String KEY = "1";

    private String username;
    private String password;

    @Override
    public void exec(Scanner scanner) {
        System.out.println("输入用户信息(id@password)");
        String[] info = null;
        while (true) {
            String input = scanner.next();
            info = input.split("@");
            if (info.length != 2) {
                System.out.println("请按照格式输入(id@password)");
            } else {
                break;
            }
        }
        username = info[0];
        password = info[1];
    }

    @Override
    public String getKey() {
        return KEY;
    }

    @Override
    public String getTip() {
        return "登陆";
    }
}
