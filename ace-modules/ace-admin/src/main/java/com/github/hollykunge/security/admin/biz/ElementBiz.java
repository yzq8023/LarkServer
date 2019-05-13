package com.github.hollykunge.security.admin.biz;

import com.ace.cache.annotation.Cache;
import com.ace.cache.annotation.CacheClear;
import com.github.hollykunge.security.admin.entity.Element;
import com.github.hollykunge.security.admin.mapper.ElementMapper;
import com.github.hollykunge.security.common.biz.BaseBiz;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Cache(key="permission:ele:u{1}")
    public List<Element> getAuthorityElementByUserId(String userId){
        //TODO:根据userId获取element return mapper.selectElementByUserId(userId)
       return null;
    }
//    public List<Element> getAuthorityElementByUserId(String userId,String menuId){
//        return mapper.selectAuthorityMenuElementByUserId(userId,menuId);
//    }

    @Cache(key="permission:ele")
    public List<Element> getAllElementPermissions(){
        //TODO:添加获取所有element的函数 return mapper.selectAllElement()
        return null;
    }

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
}
