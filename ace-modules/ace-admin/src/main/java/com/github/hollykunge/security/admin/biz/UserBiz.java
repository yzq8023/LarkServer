package com.github.hollykunge.security.admin.biz;

import com.ace.cache.annotation.Cache;
import com.ace.cache.annotation.CacheClear;
import com.github.hollykunge.security.admin.constant.AdminCommonConstant;
import com.github.hollykunge.security.admin.entity.RoleUserMap;
import com.github.hollykunge.security.admin.entity.User;
import com.github.hollykunge.security.admin.mapper.RoleUserMapMapper;
import com.github.hollykunge.security.admin.mapper.UserMapper;
import com.github.hollykunge.security.common.biz.BaseBiz;
import com.github.hollykunge.security.common.constant.UserConstant;
import com.github.hollykunge.security.common.exception.BaseException;
import com.github.hollykunge.security.common.msg.TableResultResponse;
import com.github.hollykunge.security.common.util.EntityUtils;
import com.github.hollykunge.security.common.util.Query;
import com.github.hollykunge.security.common.util.SpecialStrUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.support.SimpleTriggerContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        if (SpecialStrUtils.check(entity.getName())) {
            throw new BaseException("姓名中不能包含特殊字符...");
        }
        //校验身份证是否在数据库中存在
        User user = new User();
        user.setPId(entity.getPId());
        if (mapper.selectCount(user) > 0) {
            throw new BaseException("身份证已存在...");
        }
        String password = new BCryptPasswordEncoder(UserConstant.PW_ENCORDER_SALT).encode(defaultPassword);
        entity.setPassword(password);
        EntityUtils.setCreatAndUpdatInfo(entity);
        //用户新增添加默认字段
        entity.setDeleted("0");
        if (StringUtils.isEmpty(entity.getOrderId())) {
            entity.setOrderId(Long.parseLong(99999 + ""));
        }
        mapper.insertSelective(entity);
        return entity;
    }

    @Override
//    @CacheClear(pre = "user{1.pId}")
    public void updateSelectiveById(User entity) {
        if (SpecialStrUtils.check(entity.getName())) {
            throw new BaseException("姓名中不能包含特殊字符...");
        }
        super.updateSelectiveById(entity);
    }

    @Override
//    @CacheClear(keys = {"user","userByPid"})
    public void deleteById(String id) {
        super.deleteById(id);
    }

    /**
     * 通过userId，主键获取用户
     *
     * @param userId
     * @return
     */
    public User getUserByUserId(String userId) {
        User user = new User();

        user.setId(userId);
        return mapper.selectOne(user);
    }

    /**
     * 用户登录通过身份证号获取用户
     *
     * @param pid
     * @return
     */
//    @Cache(key = "userByPid{1}")
    public User getUserByUserPid(String pid) {
        User user = new User();

        //用户登录时通过身份证号当做用户名登录
        user.setPId(pid);
        return mapper.selectOne(user);
    }

    //    @Cache(key = "user")
    public List<User> getUsers() {
        return mapper.selectAll();
    }

    @Override
    protected String getPageName() {
        return null;
    }

    /**
     * 修改用户的角色或添加用户角色
     *
     * @param userId
     * @param roles
     */
    public void modifyRoles(String userId, String roles) {
        if (StringUtils.isEmpty(userId)) {
            throw new BaseException("userId参数为null...");
        }
        RoleUserMap roleUserMap = new RoleUserMap();
        roleUserMap.setUserId(userId);
        //删除用户角色
        roleUserMapMapper.delete(roleUserMap);
        if (!StringUtils.isEmpty(roles)) {
            String[] roleArray = roles.split(",");
            for (String role : roleArray) {
                roleUserMap.setRoleId(role);
                EntityUtils.setCreatAndUpdatInfo(roleUserMap);
                roleUserMapMapper.insertSelective(roleUserMap);
            }
        }

    }

    @Override
    public TableResultResponse<User> selectByQuery(Query query) {
        Class<User> clazz = (Class<User>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        Example example = new Example(clazz);
        if (query.entrySet().size() > 0) {
            Example.Criteria criteria = example.createCriteria();
            for (Map.Entry<String, Object> entry : query.entrySet()) {
                //如果orgCode为航天二院组织编码，则返回的数据为空
                if (AdminCommonConstant.NO_DATA_ORG_CODE.equals(entry.getValue().toString())) {
                    return new TableResultResponse<User>(query.getPageSize(), query.getPageNo(), 0, 0, new ArrayList<>());
                }
                if (SpecialStrUtils.check(entry.getValue().toString())) {
                    throw new BaseException("查询条件不能包含特殊字符...");
                }
                criteria.andLike(entry.getKey(), "%" + entry.getValue().toString() + "%");
            }
        }
        example.setOrderByClause("ORDER_ID ASC");
        Page<Object> result = PageHelper.startPage(query.getPageNo(), query.getPageSize());
        List<User> list = mapper.selectByExample(example);
        return new TableResultResponse<User>(result.getPageSize(), result.getPageNum(), result.getPages(), result.getTotal(), list);
    }
}