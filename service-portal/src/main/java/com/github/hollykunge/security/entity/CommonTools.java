package com.github.hollykunge.security.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * @description: 常用工具
 * @author: dd
 * @since: 2019-06-08
 */
@Data
@Table(name = "PORTAL_COMMONTOOLS")
public class CommonTools {
    @Column(name = "ID")
    private String id;
}
