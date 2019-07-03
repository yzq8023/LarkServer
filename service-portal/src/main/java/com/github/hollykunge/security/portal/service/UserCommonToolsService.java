package com.github.hollykunge.security.portal.service;

import com.github.hollykunge.security.common.biz.BaseBiz;
import com.github.hollykunge.security.common.exception.BaseException;
import com.github.hollykunge.security.common.util.UUIDUtils;
import com.github.hollykunge.security.entity.CardInfo;
import com.github.hollykunge.security.entity.CommonTools;
import com.github.hollykunge.security.entity.UserCard;
import com.github.hollykunge.security.entity.UserCommonTools;
import com.github.hollykunge.security.mapper.CommonToolsMapper;
import com.github.hollykunge.security.mapper.UserCommonToolsMapper;
import com.github.hollykunge.security.vo.UserCardVO;
import com.github.hollykunge.security.vo.UserCommonToolsVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 用户设置常用工具业务
 * @author zhhongyu
 * @since 2019-06-19
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserCommonToolsService extends BaseBiz<UserCommonToolsMapper, UserCommonTools> {
    @Resource
    private CommonToolsMapper commonToolsMapper;
    @Override
    protected String getPageName() {
        return null;
    }

    /**
     * 获取用户所有的常用工具，已被勾选的将defaultChecked赋值为true
     * @param userId
     * @return
     */
    public List<UserCommonToolsVO> allCommonTools(String userId,String orgCode){
        CommonTools commonTool = new CommonTools();
        commonTool.setStatus("1");
        //查询status为启用的数据
        List<CommonTools> commonTools = commonToolsMapper.select(commonTool);
        commonTools = commonTools.parallelStream().filter(new Predicate<CommonTools>() {
            @Override
            public boolean test(CommonTools commonTools) {
                if(StringUtils.isEmpty(commonTools.getOrgCode())){
                    return false;
                }
                if(orgCode.contains(commonTools.getOrgCode())){
                    return true;
                }
                return false;
            }
        }).collect(Collectors.toList());
        UserCommonTools userCommonTools = new UserCommonTools();
        userCommonTools.setUserId(userId);
        List<UserCommonTools> userCommonToolsList = mapper.select(userCommonTools);
        List<UserCommonToolsVO> result = this.setDefaultChecked(commonTools,userCommonToolsList);
        return result;
    }

    private List<UserCommonToolsVO> setDefaultChecked(List<CommonTools> commonTools,List<UserCommonTools> userCommonToolsList){
        List<UserCommonToolsVO> result = new ArrayList<>();
        commonTools.parallelStream().forEach(commonTool -> {
            UserCommonToolsVO userCommonToolsVO = new UserCommonToolsVO();
            BeanUtils.copyProperties(commonTool,userCommonToolsVO);
            if(StringUtils.isEmpty(commonTool.getId())){
                throw new BaseException("datasource contains error data...");
            }
            boolean isContains = userCommonToolsList.parallelStream().
                    anyMatch(userCommonTool -> commonTool.getId().equals(userCommonTool.getToolId()));
            if(isContains){
                userCommonToolsVO.setDefaultChecked(true);
            }else{
                userCommonToolsVO.setDefaultChecked(false);
            }
            result.add(userCommonToolsVO);
        });
        return result;
    }

    /**
     * 用户获取要显示的常用工具列表
     * @param userId
     * @return
     */
    public List<UserCommonToolsVO> userCommonTools(String userId){
        List<UserCommonToolsVO> result = new ArrayList<>();
        UserCommonTools userCommonTool = new UserCommonTools();
        userCommonTool.setUserId(userId);
        List<UserCommonTools> userCommonTools = mapper.select(userCommonTool);
        userCommonTools.stream().forEach(userTool ->{
            UserCommonToolsVO userCommonToolsVO = new UserCommonToolsVO();
            if(StringUtils.isEmpty(userTool.getToolId())){
                throw new BaseException("datasource contains error data...");
            }
            CommonTools commonTools = commonToolsMapper.selectByPrimaryKey(userTool.getToolId());
            BeanUtils.copyProperties(commonTools,userCommonToolsVO);
            if(commonTools != null){
                userCommonToolsVO.setTitle(commonTools.getTitle());
                userCommonToolsVO.setUri(commonTools.getUri());
                userCommonToolsVO.setId(commonTools.getId());
                userCommonToolsVO.setDefaultChecked(true);
            }
            result.add(userCommonToolsVO);
        });
        return result;
    }

    @Override
    public void insertSelective(UserCommonTools entity) {
        entity.setId(UUIDUtils.generateShortUuid());
        mapper.insertSelective(entity);
    }
}
