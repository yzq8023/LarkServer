package com.github.hollykunge.security.admin.biz;

import com.github.hollykunge.security.admin.entity.ResourceRoleMap;
import com.github.hollykunge.security.admin.mapper.ResourceRoleMapMapper;
import com.github.hollykunge.security.common.biz.BaseBiz;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by 协同设计小组 on 2017/6/19.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ResourceRoleMapBiz extends BaseBiz<ResourceRoleMapMapper,ResourceRoleMap> {
    @Override
    protected String getPageName() {
        return null;
    }
}
