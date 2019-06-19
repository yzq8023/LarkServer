package com.github.hollykunge.security.portal.service;

import com.github.hollykunge.security.common.biz.BaseBiz;
import com.github.hollykunge.security.entity.HeatMap;
import com.github.hollykunge.security.mapper.HotmapMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 工作热力图业务实现
 * @author zhhongyu
 * @since 2019-06-19
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class HotMapService extends BaseBiz<HotmapMapper, HeatMap> {
    @Override
    protected String getPageName() {
        return null;
    }
}
