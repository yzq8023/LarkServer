package com.github.hollykunge.security.portal.service;

import com.github.hollykunge.security.common.biz.BaseBiz;
import com.github.hollykunge.security.entity.CardInfo;
import com.github.hollykunge.security.mapper.CardInfoMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 卡片主表信息业务类
 * @author zhhongyu
 * @since 2019-06-11
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CardService extends BaseBiz<CardInfoMapper, CardInfo> {
    @Override
    protected String getPageName() {
        return null;
    }
}
