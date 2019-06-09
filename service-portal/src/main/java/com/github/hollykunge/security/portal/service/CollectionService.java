package com.github.hollykunge.security.portal.service;

import com.github.hollykunge.security.common.biz.BaseBiz;
import com.github.hollykunge.security.entity.Collection;
import com.github.hollykunge.security.mapper.CollectionMapper;

import java.util.List;

/**
 * @description: 工作台
 * @author: dd
 * @since: 2019-06-08
 */
public class CollectionService extends BaseBiz<CollectionMapper, Collection> {
    @Override
    protected String getPageName() {
        return null;
    }

    public List<Collection> selectLastFive(String id){
        return mapper.selectLastFiveByUserId(id);
    }
}
