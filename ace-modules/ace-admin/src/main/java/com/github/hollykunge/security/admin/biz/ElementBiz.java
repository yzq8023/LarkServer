package com.github.hollykunge.security.admin.biz;

import com.ace.cache.annotation.Cache;
import com.ace.cache.annotation.CacheClear;
import com.github.hollykunge.security.admin.entity.Element;
import com.github.hollykunge.security.admin.entity.Role;
import com.github.hollykunge.security.admin.mapper.ElementMapper;
import com.github.hollykunge.security.common.biz.BaseBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author 协同设计小组
 * @create 2017-06-23 20:27
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ElementBiz extends BaseBiz<ElementMapper,Element> {
    @Autowired
    private RoleBiz roleBiz;
//    @Cache(key="permission:ele:u{1}")
//    public List<Element> getAuthorityElementByUserId(String userId){
//       return mapper.selectAuthorityElementByUserId(userId);
//    }
//    public List<Element> getAuthorityElementByUserId(String userId,String menuId){
//        return mapper.selectAuthorityMenuElementByUserId(userId,menuId);
//    }
//
//    @Cache(key="permission:ele")
//    public List<Element> getAllElementPermissions(){
//        return mapper.selectAllElementPermissions();
//    }

    @Override
    @CacheClear(keys={"permission:ele","permission"})
    public void insertSelective(Element entity) {
        super.insertSelective(entity);
    }

    @Override
    @CacheClear(keys={"permission:ele","permission"})
    public void updateSelectiveById(Element entity) {
        super.updateSelectiveById(entity);
    }

    @Override
    protected String getPageName() {
        return null;
    }

    /**
     * 通过userId获取权限下的Element
     * @param userId
     * @return
     */
    public List<Element> getElementByUserId(String userId){
        List<Element> result = new ArrayList<>();
        List<Role> roleList = roleBiz.getRoleByUserId(userId);
        roleList.stream().forEach(roleEntity ->{
            List<Element> roleElemet = mapper.getElemntByRoleId(roleEntity.getId());
            result.addAll(roleElemet);
        });
        return result;
    }
}
