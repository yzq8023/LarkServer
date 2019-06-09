package com.github.hollykunge.security.entity;

import lombok.Data;

import javax.persistence.Column;
import java.util.Date;

/**
 * @description: 工作台-研讨
 * @author: dd
 * @since: 2019-06-07
 */
@Data
public class Message {
    @Column(name = "ID")
    private String id;
    @Column(name = "MSG")
    private String msg;
    @Column(name = "MSG_SENDER")
    private String msgSender;
    @Column(name = "FROM_USERNAME")
    private String fromUsername;
    @Column(name = "SEND_TIME")
    private Date sendTime;
    @Column(name = "READ")
    private String read;
    @Column(name = "MSG_TYPE")
    private String msgType;
    @Column(name = "LEVELS")
    private String levels;
}
