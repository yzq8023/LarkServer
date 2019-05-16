package com.github.hollykunge.security.admin.biz;

import com.ace.cache.annotation.Cache;
import com.ace.cache.annotation.CacheClear;
import com.github.hollykunge.security.admin.entity.User;
import com.github.hollykunge.security.admin.mapper.MenuMapper;
import com.github.hollykunge.security.admin.mapper.UserMapper;
import com.github.hollykunge.security.api.vo.user.UserInfo;
import com.github.hollykunge.security.auth.client.jwt.UserAuthUtil;
import com.github.hollykunge.security.common.biz.BaseBiz;
import com.github.hollykunge.security.common.constant.UserConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.parser.Entity;
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author 协同设计小组
 * @create 2017-06-08 16:23
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserBiz extends BaseBiz<UserMapper,User> {

    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private UserAuthUtil userAuthUtil;
    @Override
    public void insertSelective(User entity) {
        String password = new BCryptPasswordEncoder(UserConstant.PW_ENCORDER_SALT).encode(entity.getPassword());
        entity.setPassword(password);
        super.insertSelective(entity);
    }

    @Override
    @CacheClear(pre="user{1.username}")
    public void updateSelectiveById(User entity) {
        super.updateSelectiveById(entity);
    }

    @Override
    protected String getPageName() {
        return null;
    }

    /**
     * 根据用户名获取用户信息
     * @param username
     * @return
     */
    @Cache(key="user{1}")
    public User getUserByUsername(String username){
        User user = new User();
        user.setUsername(username);
        return mapper.selectOne(user);
    }


    /**
     * 根据用户名获取用户信息
     * @param userId
     * @return
     */
    @Cache(key="user{1}")
    public User getUserByUserId(Integer userId){
        User user = new User();
        user.setId(userId);
        return mapper.selectOne(user);
    }

    /**
     * 获取所有用户信息
     * @return
     */
    @Cache(key="user")
    public List<User> getUsers(){
        return mapper.selectAll();
    }

    /**
     * 新增用户
     * @param entity
     * @return
     */
    public boolean addUser(User entity){
        return mapper.insert(entity) > 0 ? true:false;
    }

    /**
     * 删除用户
     * @param entity
     * @param id
     * @return
     */
    public boolean removeUserById(User entity,String id){
        entity.setId(id);
        return  mapper.delete(entity) > 0 ? true:false;
    }

    /**
     *修改用户信息
     * @param entity
     * @param id
     * @return
     */
    public boolean updateUserById(User entity,String id){
        entity.setId(id);
        return  mapper.updateByPrimaryKey(entity) > 0 ? true:false;
    }

}
