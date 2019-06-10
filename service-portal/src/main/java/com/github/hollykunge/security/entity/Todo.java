package com.github.hollykunge.security.entity;

import lombok.Data;

import javax.persistence.Column;
import java.util.Date;
/**
 * @description: 工作台-待办
 * @author: dd
 * @since: 2019-06-07
 */
@Data
public class Todo {
    @Column(name = "ID")
    private String id;
    @Column(name = "CONTENT")
    private String content;
    @Column(name = "START_TIME")
    private Date startTime;
    @Column(name = "END_TIME")
    private Date endTime;
    @Column(name = "TYPE")
    private String type;
    @Column(name = "DONE")
    private Boolean done;
}
