package com.github.hollykunge.security.admin.biz;

import com.ace.cache.annotation.Cache;
import com.ace.cache.annotation.CacheClear;
import com.github.hollykunge.security.admin.entity.RoleUserMap;
import com.github.hollykunge.security.admin.entity.User;
import com.github.hollykunge.security.admin.mapper.RoleUserMapMapper;
import com.github.hollykunge.security.admin.mapper.UserMapper;
import com.github.hollykunge.security.common.biz.BaseBiz;
import com.github.hollykunge.security.common.constant.UserConstant;
import com.github.hollykunge.security.common.exception.BaseException;
import com.github.hollykunge.security.common.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 协同设计小组
 * @create 2017-06-08 16:23
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class UserBiz extends BaseBiz<UserMapper, User> {
    @Value("${admin.create-user.defaultPassword}")
    private String defaultPassword;

    @Resource
    private RoleUserMapMapper roleUserMapMapper;

    public User addUser(User entity) {
        //校验身份证是否在数据库中存在
        User user = new User();
        user.setPId(entity.getPId());
        if(mapper.selectCount(user)>0){
            throw new BaseException("身份证已存在...");
        }
        String password = new BCryptPasswordEncoder(UserConstant.PW_ENCORDER_SALT).encode(defaultPassword);
        entity.setPassword(password);
        EntityUtils.setCreatAndUpdatInfo(entity);
        mapper.insertSelective(entity);
        return entity;
    }

    @Override
    @CacheClear(pre = "user{1.pId}")
    public void updateSelectiveById(User entity) {
        super.updateSelectiveById(entity);
    }

    @Override
    @CacheClear(keys = {"user","userByPid"})
    public void deleteById(Object id) {
        super.deleteById(id);
    }

    /**
     * 通过userId，主键获取用户
     * @param userId
     * @return
     */
    @Cache(key = "user{1}")
    public User getUserByUserId(String userId) {
        User user = new User();

        user.setId(userId);
        return mapper.selectOne(user);
    }

    /**
     * 用户登录通过身份证号获取用户
     * @param pid
     * @return
     */
    @Cache(key = "userByPid{1}")
    public  User getUserByUserPid(String pid){
        User user = new User();

        //用户登录时通过身份证号当做用户名登录
        user.setPId(pid);
        return mapper.selectOne(user);
    }

    @Cache(key = "user")
    public List<User> getUsers() {
        return mapper.selectAll();
    }

    @Override
    protected String getPageName() {
        return null;
    }

    /**
     * 修改用户的角色或添加用户角色
     * @param userId
     * @param roles
     */
    public void modifyRoles(String userId,String roles){
        if(StringUtils.isEmpty(userId)){
            throw new BaseException("userId参数为null...");
        }
        RoleUserMap roleUserMap = new RoleUserMap();
        roleUserMap.setUserId(userId);
        //删除用户角色
        roleUserMapMapper.delete(roleUserMap);
        if(!StringUtils.isEmpty(roles)){
            String[] roleArray = roles.split(",");
            for (String role:roleArray) {
                roleUserMap.setRoleId(role);
                EntityUtils.setCreatAndUpdatInfo(roleUserMap);
                roleUserMapMapper.insertSelective(roleUserMap);
            }
        }

    }
}