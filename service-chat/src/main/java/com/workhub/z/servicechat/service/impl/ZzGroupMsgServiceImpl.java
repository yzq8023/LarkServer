package com.workhub.z.servicechat.service.impl;

import com.github.hollykunge.security.common.biz.BaseBiz;
import com.github.hollykunge.security.common.msg.TableResultResponse;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.workhub.z.servicechat.config.common;
import com.workhub.z.servicechat.dao.ZzGroupMsgDao;
import com.workhub.z.servicechat.entity.ZzGroupMsg;
import com.workhub.z.servicechat.service.ZzGroupMsgService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 群组消息表(ZzGroupMsg)表服务实现类
 *
 * @author 忠
 * @since 2019-05-10 11:38:02
 */
@Service("zzGroupMsgService")
public class ZzGroupMsgServiceImpl extends BaseBiz<ZzGroupMsgDao, ZzGroupMsg> implements ZzGroupMsgService {
    @Resource
    private ZzGroupMsgDao zzGroupMsgDao;

    /**
     * 通过ID查询单条数据
     *
     * @param msgId 主键
     * @return 实例对象
     */
    @Override
    public ZzGroupMsg queryById(String msgId) {
        //return this.zzGroupMsgDao.queryById(msgId);
        ZzGroupMsg entity = new ZzGroupMsg();
        entity.setMsgId(msgId);
        return super.selectOne(entity);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<ZzGroupMsg> queryAllByLimit(int offset, int limit) {
        return this.zzGroupMsgDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param zzGroupMsg 实例对象
     * @return 实例对象
     */
    @Override
    @Transactional
    public void insert(ZzGroupMsg zzGroupMsg) {
        int insert = this.zzGroupMsgDao.insert(zzGroupMsg);
//        return insert;
//        super.insert(zzGroupMsg);
    }

    @Override
    protected String getPageName() {
        return null;
    }

    /**
     * 修改数据
     *
     * @param zzGroupMsg 实例对象
     * @return 实例对象
     */
    @Override
    @Transactional
    public void update(ZzGroupMsg zzGroupMsg) {
        /*int update = this.zzGroupMsgDao.update(zzGroupMsg);
        return update;*/
        //super.updateById(zzGroupMsg);
        int update = this.zzGroupMsgDao.update(zzGroupMsg);
    }

    /**
     * 通过主键删除数据
     *
     * @param msgId 主键
     * @return 是否成功
     */
    @Override
    @Transactional
    public void deleteById(String msgId) {
       // return this.zzGroupMsgDao.deleteById(msgId) > 0;
        ZzGroupMsg entity = new ZzGroupMsg();
        entity.setMsgId(msgId);
        super.delete(entity);
    }
    /**
     * 查询消息记录
     * @auther zhuqz
     * @param param 参数集合：sender发送人，receiver接收人，begin_time开始时间，end_time结束时间
     * @return 对象列表
     */
    public TableResultResponse<ZzGroupMsg> queryMsg(Map<String,String> param) throws Exception{
        List<ZzGroupMsg> dataList = null;
        String begin_time = param.get("begin_time");//查询开始时间
        if(begin_time==null || "".equals(begin_time)){
            begin_time="2019-01-01";//时间的无穷小
        }
        String end_time = param.get("end_time");//查询结束时间
        if(end_time==null || "".equals(end_time)){
            end_time="2999-12-31";//时间的无穷大
        }


        //目前数据库消息备份是每个月一号凌晨备份上上个月的记录，同时删除当前消息记录
        //例如当前日期是2019-06-01 那么定时任务把2019-05-01之前的消息记录备份到历史表，同时删除当前表的记录
        //所有如果以后定时任务的逻辑有变化，这里查询逻辑也要做修改
        int pageNum = Integer.valueOf(param.get("page"));
        int pageSize = Integer.valueOf(param.get("size"));
        PageHelper.startPage(pageNum, pageSize);

        String lastMonthFirstDay= common.getBeforeMonthFirstDay();//上个月第一天
        if(begin_time.compareTo(lastMonthFirstDay)>=0){//如果当前查询开始时间大于上个月1号，当期表
            dataList=this.zzGroupMsgDao.queryMsgRecent(param);
        }else if(end_time.compareTo(lastMonthFirstDay)<0){//如果当前查询结束时间小于上个月1号，只查询历史表
            dataList=this.zzGroupMsgDao.queryMsgHis(param);
        }else{//询历史表+最近表
            dataList=this.zzGroupMsgDao.queryMsgCurrentAndHis(param);
        }
        PageInfo<ZzGroupMsg> pageInfo = new PageInfo<>(dataList);
        TableResultResponse<ZzGroupMsg> res = new TableResultResponse<ZzGroupMsg>(
                pageInfo.getPageSize(),
                pageInfo.getPageNum(),
                pageInfo.getPages(),
                pageInfo.getTotal(),
                pageInfo.getList()
        );
        return res;
    }
}