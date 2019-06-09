package com.github.hollykunge.security.portal.service;

import com.github.hollykunge.security.common.biz.BaseBiz;
import com.github.hollykunge.security.entity.Message;
import com.github.hollykunge.security.mapper.MessageMapper;

/**
 * @description: 工作台
 * @author: dd
 * @since: 2019-06-08
 */
public class MessageService extends BaseBiz<MessageMapper, Message> {
    @Override
    protected String getPageName() {
        return null;
    }
}
