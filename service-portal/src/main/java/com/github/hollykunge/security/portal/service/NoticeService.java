package com.github.hollykunge.security.portal.service;

import com.github.hollykunge.security.common.biz.BaseBiz;
import com.github.hollykunge.security.common.exception.BaseException;
import com.github.hollykunge.security.entity.Notice;
import com.github.hollykunge.security.mapper.NoticeMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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

    @Override
    public List<Notice> selectList(Notice entity) {
        if(StringUtils.isEmpty(entity.getOrgCode())){
            throw new BaseException("当前登录人没有组织编码...");
        }
        List<Notice> notices = mapper.selectAll();
        notices = notices.parallelStream().filter(new Predicate<Notice>() {
            @Override
            public boolean test(Notice notice) {
                if(StringUtils.isEmpty(notice.getOrgCode())){
                    return false;
                }
                if(entity.getOrgCode().contains(notice.getOrgCode())){
                    return true;
                }
                return false;
            }
        }).collect(Collectors.toList());
        return notices;
    }
}
