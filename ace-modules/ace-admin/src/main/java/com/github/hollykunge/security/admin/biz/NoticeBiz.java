package com.github.hollykunge.security.admin.biz;

import com.github.hollykunge.security.admin.entity.Notice;
import com.github.hollykunge.security.admin.entity.User;
import com.github.hollykunge.security.admin.mapper.NoticeMapper;

import com.github.hollykunge.security.admin.mapper.UserMapper;
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

//    @Resource
//    private UserMapper userMapper;
//
//    public List<Notice> listNoticeTopSix(String userId){
//        List<Notice> noticeByUserIdTopSix = mapper.getNoticeByUserIdTopSix(userId);
//        return noticeByUserIdTopSix;
//    }
//
//    public String getOrgIdByUserId(String userId) {
//        Example example = new Example(User.class);
//        example.createCriteria().andEqualTo("id", userId);
//        List<User> orgUserMaps = userMapper.selectByExample(example);
//        return orgUserMaps.get(0).getOrgCode();
//    }
    @Override
    protected String getPageName() {
        return null;
    }
}
