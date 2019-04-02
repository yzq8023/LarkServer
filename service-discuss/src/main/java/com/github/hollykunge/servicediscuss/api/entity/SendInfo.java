package com.github.hollykunge.servicediscuss.api.entity;

import lombok.Data;

/**
 * @description: websocket通信json封装
 * @author: dd
 * @since: 2019-02-14
 */
@Data
public class SendInfo {

    /**
     * 发送信息的代码
     */
    private String code;

    /**
     * 信息
     */
    private Message message;
}
