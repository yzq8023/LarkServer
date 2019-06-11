package com.github.hollykunge.security.portal.service;

import com.github.hollykunge.security.common.biz.BaseBiz;
import com.github.hollykunge.security.entity.Notice;
import com.github.hollykunge.security.mapper.NoticeMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @description: 工作台
 * @author: dd
 * @since: 2019-06-08
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class NoticeService extends BaseBiz<NoticeMapper, Notice> {
    @Override
    protected String getPageName() {
        return null;
    }
}
