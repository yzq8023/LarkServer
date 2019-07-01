package com.github.hollykunge.security.portal.service;

import com.github.hollykunge.security.common.biz.BaseBiz;
import com.github.hollykunge.security.common.exception.BaseException;
import com.github.hollykunge.security.common.util.UUIDUtils;
import com.github.hollykunge.security.entity.CommonTools;
import com.github.hollykunge.security.mapper.CommonToolsMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * 常用工具业务
 * @author zhhongyu
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CommonToolsService extends BaseBiz<CommonToolsMapper, CommonTools> {
    @Override
    protected String getPageName() {
        return null;
    }

    @Override
    public void insertSelective(CommonTools entity) {
        if(StringUtils.isEmpty(entity.getOrgCode())){
            throw new BaseException("组织代码不能为空...");
        }
        entity.setId(UUIDUtils.generateShortUuid());
        //添加时状态默认为1
        entity.setStatus("1");
        mapper.insertSelective(entity);
    }
}
