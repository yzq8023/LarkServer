package com.github.hollykunge.security.portal.service;

import com.github.hollykunge.security.common.biz.BaseBiz;
import com.github.hollykunge.security.entity.FeedBack;
import com.github.hollykunge.security.mapper.FeedBackMapper;

/**
 * @ClassName FeedBackService
 * @Description TODO
 * @Author hollykunge
 * @Date 2019/6/30 13:29
 * @Version 1.0
 **/
public class FeedBackService extends BaseBiz<FeedBackMapper, FeedBack> {
    @Override
    protected String getPageName() {
        return null;
    }
}
