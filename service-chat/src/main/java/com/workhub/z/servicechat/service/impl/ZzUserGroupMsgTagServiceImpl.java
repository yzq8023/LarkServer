package com.workhub.z.servicechat.service.impl;

import com.github.hollykunge.security.common.biz.BaseBiz;
import com.github.hollykunge.security.common.msg.TableResultResponse;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.workhub.z.servicechat.dao.ZzUserGroupMsgTagDao;
import com.workhub.z.servicechat.entity.ZzUserGroupMsgTag;
import com.workhub.z.servicechat.service.ZzUserGroupMsgTagService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户标记群消息
 *
 * @author zhuqz
 * @since 2019-06-11
 */
@Service("zzUserGroupMsgTagService")
public class ZzUserGroupMsgTagServiceImpl  extends BaseBiz<ZzUserGroupMsgTagDao, ZzUserGroupMsgTag> implements ZzUserGroupMsgTagService {
    @Resource
    private ZzUserGroupMsgTagDao zzUserGroupMsgTagDao;
    @Override
    protected String getPageName() {
        return null;
    }
    /**
     * 增加群消息标记
     * @param entity 数据库实体
     * @return  1成功；0用户不在组内或者组已经不存在；-1错误
     * @author zhuqz
     * @since 2019-06-11
     */
    public String addUserGroupMsgTag(ZzUserGroupMsgTag entity) throws Exception{
        String res="1";
        //调用父类方法目前报错，暂时改成自己的方法，如果以后父类方法修改好，应该改成走父类方法。
        //super.insert(entity);
        //调用自己的方法新增数据
        int rows = this.zzUserGroupMsgTagDao.insert(entity);
        return  res;
    }
    /**
     * 按照id删除群消息标记
     * @param id 数据库id
     * @return  1成功；0用户不在组内或者组已经不存在；-1错误
     * @author zhuqz
     * @since 2019-06-11
     */
    public String deleteById(String id) throws Exception{
        String res="1";
        ZzUserGroupMsgTag entity = new ZzUserGroupMsgTag();
        entity.setId(id);
        super.delete(entity);
        return  res;
    }
    /**
     * 按照条件（用户id，群id，消息id，标记类型）删除群消息标记
     * @param entity 数据库实体
     * @return  1成功；0用户不在组内或者组已经不存在；-1错误
     * @author zhuqz
     * @since 2019-06-11
     */
    public String deleteByConditions(ZzUserGroupMsgTag entity) throws Exception{
        String res="1";
        //如果没有用户id，设置一个不存在的值，防止数据误删
        if((entity.getUserId()==null || entity.getUserId().equals(""))){
            entity.setUserId("nodata");
        }
        int rows = this.zzUserGroupMsgTagDao.deleteByConditions(entity);
        return  res;
    }

    /**
     * 查询用户标记消息（自己实现分页，目前父类分页不支持排序、查询条件复杂化）
     * @param
     * @return  数据列表
     * @author zhuqz
     * @since 2019-06-11
     */
    public TableResultResponse<ZzUserGroupMsgTag> getUserGroupMsgTagList(String userId,String groupId,String tagType,int pageNum,int pageSize) throws Exception{

        //调用父类查询分页begin
        /*Map<String, Object> params = new HashMap<>();
            if(!(groupId==null || groupId.equals(""))){
                params.put("groupId",groupId);
            }
            if(!(tagType==null || tagType.equals(""))){
                params.put("tagType",tagType);
            }
            if(!(userId==null || userId.equals(""))){
                params.put("userId",userId);
            }
            params.put("pageNo",pageNum);
            params.put("pageSize",pageSize);
            Query query = new Query(params);
            return  super.selectByQuery(query);*/
        //调用父类查询分页end

        //自己写分页
        PageHelper.startPage(pageNum, pageSize);
        List<ZzUserGroupMsgTag> list = this.zzUserGroupMsgTagDao.getInfList(userId,groupId,tagType);
        PageInfo<ZzUserGroupMsgTag> pageInfo = new PageInfo<>(list);
        TableResultResponse<ZzUserGroupMsgTag> res = new TableResultResponse<ZzUserGroupMsgTag>(
                pageInfo.getPageSize(),
                pageInfo.getPageNum(),
                pageInfo.getPages(),
                pageInfo.getTotal(),
                pageInfo.getList()
        );
        return res;
    }
}
