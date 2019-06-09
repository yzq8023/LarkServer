package com.github.hollykunge.security.entity;

import lombok.Data;

import javax.persistence.Column;

/**
 * @description: 工作台-我的收藏
 * @author: dd
 * @since: 2019-06-07
 */
@Data
public class Collection {
    @Column(name = "ID")
    private String id;
    @Column(name = "COLLECTION_NAME")
    private String collectionName;
    @Column(name = "COLLECTION_ID")
    private String collectionId;
}
