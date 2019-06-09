package com.github.hollykunge.security.portal.service;

import com.github.hollykunge.security.common.biz.BaseBiz;
import com.github.hollykunge.security.entity.Todo;
import com.github.hollykunge.security.mapper.TodoMapper;

/**
 * @description: 工作台
 * @author: dd
 * @since: 2019-06-08
 */
public class TodoService extends BaseBiz<TodoMapper, Todo> {
    @Override
    protected String getPageName() {
        return null;
    }
}
