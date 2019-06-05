package com.github.hollykunge.security.admin.biz;

import com.github.hollykunge.security.admin.entity.Notice;
import com.github.hollykunge.security.admin.entity.OrgUserMap;
import com.github.hollykunge.security.admin.mapper.NoticeMapper;
import com.github.hollykunge.security.admin.mapper.OrgUserMapMapper;
import com.github.hollykunge.security.common.biz.BaseBiz;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: yzq
 * @Date: 创建于 2019/6/4 16:09
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class NoticeBiz extends BaseBiz<NoticeMapper,Notice>{

    @Resource
    private OrgUserMapMapper orgUserMapMapper;

    public String getOrgIdByUserId(String userId){
        Example example = new Example(OrgUserMap.class);
        example.createCriteria().andEqualTo("userId",userId);
        List<OrgUserMap> orgUserMaps = orgUserMapMapper.selectByExample(example);
        return orgUserMaps.get(0).getUserId();
    }
    @Override
    protected String getPageName() {
        return null;
    }
}
