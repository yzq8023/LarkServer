package com.workhub.z.servicechat.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.hollykunge.security.common.msg.TableResultResponse;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.workhub.z.servicechat.VO.SingleMessageVO;
import com.workhub.z.servicechat.config.common;
import com.workhub.z.servicechat.dao.ZzMessageInfoDao;
import com.workhub.z.servicechat.entity.ZzMessageInfo;
import com.workhub.z.servicechat.model.ContactsMessageDto;
import com.workhub.z.servicechat.service.ZzMessageInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.workhub.z.servicechat.config.common.aggregation;

/**
 * 消息存储(ZzMessageInfo)表服务实现类
 *
 * @author makejava
 * @since 2019-06-23 13:50:41
 */
@Service("zzMessageInfoService")
public class ZzMessageInfoServiceImpl implements ZzMessageInfoService {
    private static Logger log = LoggerFactory.getLogger(ZzMessageInfoServiceImpl.class);
    @Resource
    private ZzMessageInfoDao zzMessageInfoDao;

    /**
     * 通过ID查询单条数据
     *
     * @param msgId 主键
     * @return 实例对象
     */
    @Override
    public ZzMessageInfo queryById(String msgId) {
        ZzMessageInfo zzMessageInfo = this.zzMessageInfoDao.queryById(msgId);
        try {
            common.putVoNullStringToEmptyString(zzMessageInfo);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(common.getExceptionMessage(e));
        }
        return zzMessageInfo;
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<ZzMessageInfo> queryAllByLimit(int offset, int limit) {
        return this.zzMessageInfoDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param zzMessageInfo 实例对象
     * @return 实例对象
     */
    @Override
    public ZzMessageInfo insert(ZzMessageInfo zzMessageInfo) {
        this.zzMessageInfoDao.insert(zzMessageInfo);
        return zzMessageInfo;
    }

    /**
     * 修改数据
     *
     * @param zzMessageInfo 实例对象
     * @return 实例对象
     */
    @Override
    public ZzMessageInfo update(ZzMessageInfo zzMessageInfo) {
        this.zzMessageInfoDao.update(zzMessageInfo);
        return this.queryById(zzMessageInfo.getMsgId());
    }

    /**
     * 通过主键删除数据
     *
     * @param msgId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String msgId) {
        return this.zzMessageInfoDao.deleteById(msgId) > 0;
    }

    /**
    *@Description: 查询最近联系人
    *@Param:
    *@return:
    *@Author: 忠
    *@date: 2019/6/23
    */
    public String queryContactsMessage(String userId){
        List<ContactsMessageDto> contactsMessageDtoList =this.zzMessageInfoDao.queryContactsMessage(userId);

        List<List<ContactsMessageDto>> list2 = aggregation(contactsMessageDtoList, new Comparator<ContactsMessageDto>() {

            @Override
            public int compare(ContactsMessageDto o1, ContactsMessageDto o2) {
//                return o1.getContactsId() == o2.getContactsId() ? 0:-1;
                //按联系人分组
                return o1.getContactsId().compareTo(o2.getContactsId());

            }
        });
        List<List<ContactsMessageDto>> list3 = list2;
        String s = JSON.toJSONString(list2);
        String s1 = "[";
        for (int j = 0; j < list2.size(); j++) {
             s1 +="[";
             for (int i = 0; i < list2.get(j).size(); i++) {
             if (i == 0){
                 s1 += "\"";
                 s1 += list2.get(j).get(i).getContactsId();
                 s1 +="\",";
                  }
             s1 += list2.get(j).get(i).getContent();

             if (i==list2.get(j).size()-1){ }
              else {
                   s1 += ",";
              }
         }
                    if (j==list2.size()-1){
                        s1 +="]";
                    }else {
                        s1 +="],";
                    }
                }
        s1 +="]";
        return  s1;
    }
    public TableResultResponse queryHistoryMessageForSingle(String userId, String contactId, String isGroup, String page, String size){
        int pageNum=1;
        int pageSize=10;
        try {
            pageNum=Integer.valueOf(page);
            pageSize=Integer.valueOf(size);
        }catch (Exception e){
            e.printStackTrace();
            log.error(common.getExceptionMessage(e));
        }
        PageHelper.startPage(pageNum, pageSize);
        List<String> dataList=null;
        if("true".equals(isGroup)){
            dataList=this.zzMessageInfoDao.queryHistoryMessageForSingleGroup(userId,contactId);
        }else{
            dataList=this.zzMessageInfoDao.queryHistoryMessageForSinglePrivate(userId,contactId);
        }
        PageInfo pageInfo = new PageInfo<>(dataList);
        List<SingleMessageVO> voList=new ArrayList<>();
        for(String temp:dataList){
            SingleMessageVO vo = JSON.parseObject(temp, SingleMessageVO.class);
            voList.add(vo);
        }
        try {
            common.putVoNullStringToEmptyString(voList);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(common.getExceptionMessage(e));
        }

        TableResultResponse res = new TableResultResponse(
                pageInfo.getPageSize(),
                pageInfo.getPageNum(),
                pageInfo.getPages(),
                pageInfo.getTotal(),
                voList
        );

        return  res;
    }
}