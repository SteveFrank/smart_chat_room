package com.smart.im.client.command;

import java.util.Scanner;

/**
 * @author frankq
 * @date 2021/9/17
 */
public interface BaseCommand {

    /**
     * 从控制台提取业务数据
     */
    void exec(Scanner scanner);

    /**
     * 获取命令key
     */
    String getKey();

    /**
     * 获取命令提示信息
     */
    String getTip();
}
