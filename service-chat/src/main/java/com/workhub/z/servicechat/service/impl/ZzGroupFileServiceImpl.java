package com.workhub.z.servicechat.service.impl;

import com.github.hollykunge.security.common.msg.TableResultResponse;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.workhub.z.servicechat.VO.FileMonitoringVO;
import com.workhub.z.servicechat.VO.GroupFileVo;
import com.workhub.z.servicechat.config.common;
import com.workhub.z.servicechat.dao.ZzGroupFileDao;
import com.workhub.z.servicechat.entity.ZzGroupFile;
import com.workhub.z.servicechat.entity.ZzUploadFile;
import com.workhub.z.servicechat.feign.IFileUploadService;
import com.workhub.z.servicechat.service.ZzGroupFileService;
import jodd.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.workhub.z.servicechat.config.common.putEntityNullToEmptyString;

/**
 * 群文件(ZzGroupFile)表服务实现类
 *
 * @author 忠
 * @since 2019-05-13 10:59:08
 */
@Service("zzGroupFileService")
public class ZzGroupFileServiceImpl implements ZzGroupFileService {
    private static Logger log = LoggerFactory.getLogger(ZzGroupFileServiceImpl.class);
    @Resource
    private ZzGroupFileDao zzGroupFileDao;
    @Resource
    private IFileUploadService iFileUploadService;
    /**
     * 通过ID查询单条数据
     *
     * @param fileId 主键
     * @return 实例对象
     */
    @Override
    public ZzGroupFile queryById(String fileId) {

        //return this.zzGroupFileDao.queryById(fileId);
        /*ZzGroupFile entity = new ZzGroupFile();
        entity.setFileId(fileId);
        return super.selectOne(entity);*/
        ZzGroupFile zzGroupFile = this.zzGroupFileDao.queryById(fileId);
        try {
            common.putVoNullStringToEmptyString(zzGroupFile);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(common.getExceptionMessage(e));
        }
        return zzGroupFile;
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<ZzGroupFile> queryAllByLimit(int offset, int limit) {
        return this.zzGroupFileDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param zzGroupFile 实例对象
     * @return 实例对象
     */
    @Override
    @Transactional
    public void insert(ZzGroupFile zzGroupFile) {
        try {
            putEntityNullToEmptyString(zzGroupFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int insert = this.zzGroupFileDao.insert(zzGroupFile);
//        return insert;
        //super.insert(zzGroupFile);
    }

   /* @Override
    protected String getPageName() {
        return null;
    }*/

    /**
     * 修改数据
     *
     * @param zzGroupFile 实例对象
     * @return 实例对象
     */
    @Override
    @Transactional
    public void update(ZzGroupFile zzGroupFile) {
        /*int update = this.zzGroupFileDao.update(zzGroupFile);
        return update;*/
        //super.updateById(zzGroupFile);
        this.zzGroupFileDao.update(zzGroupFile);
    }

    /**
     * 通过主键删除数据
     *
     * @param fileId 主键
     * @return 是否成功
     */
    @Override
    @Transactional
    public void deleteById(String fileId) {

        //return this.zzGroupFileDao.deleteById(fileId) > 0;
       /* ZzGroupFile entity = new ZzGroupFile();
        entity.setFileId(fileId);
        super.delete(entity);*/
        this.zzGroupFileDao.deleteById(fileId);
    }

    /**
     * 查询群内文件信息
     * @param id
     * @param page
     * @param size
     * @param query 文件名称
     * @return
     * @throws Exception
     */
    @Override
    public TableResultResponse<GroupFileVo> groupFileList(String id,String userId,String query, int page, int size) throws Exception {
        if (StringUtil.isEmpty(id)) throw new NullPointerException("id is null");
        PageHelper.startPage(page, size);
        List<GroupFileVo> dataList =this.zzGroupFileDao.groupFileList(id,userId,query);
        //null的String类型属性转换空字符串
        common.putVoNullStringToEmptyString(dataList);
        PageInfo<GroupFileVo> pageInfo = new PageInfo<>(dataList);
        TableResultResponse<GroupFileVo> res = new TableResultResponse<GroupFileVo>(
                pageInfo.getPageSize(),
                pageInfo.getPageNum(),
                pageInfo.getPages(),
                pageInfo.getTotal(),
                pageInfo.getList()
        );
        return res;
    }


    /**
     * 查询群内我上传文件信息
     * @param groupId
     * @param page
     * @param size
     * @param userId
     * @return
     * @throws Exception
     */
    @Override
    public TableResultResponse<GroupFileVo> groupFileListByMe(String groupId,String userId, int page, int size) throws Exception {
        if (StringUtil.isEmpty(groupId)) throw new NullPointerException("id is null");
        PageHelper.startPage(page, size);
        List<GroupFileVo> dataList =this.zzGroupFileDao.groupFileListByMe(groupId,userId);
        //null的String类型属性转换空字符串
        common.putVoNullStringToEmptyString(dataList);
        PageInfo<GroupFileVo> pageInfo = new PageInfo<>(dataList);
        TableResultResponse<GroupFileVo> res = new TableResultResponse<GroupFileVo>(
                pageInfo.getPageSize(),
                pageInfo.getPageNum(),
                pageInfo.getPages(),
                pageInfo.getTotal(),
                pageInfo.getList()
        );
        return res;
    }

    /**
     * 查询群内待审批文件信息
     * @param groupId
     * @param page
     * @param size
     * @param userId
     * @return
     * @throws Exception
     */
    @Override
    public TableResultResponse<GroupFileVo> groupFileListByOwner(String groupId,String userId, int page, int size) throws Exception {
        if (StringUtil.isEmpty(groupId)) throw new NullPointerException("id is null");
        PageHelper.startPage(page, size);
        List<GroupFileVo> dataList =this.zzGroupFileDao.groupFileListByOwner(groupId,userId);
        //null的String类型属性转换空字符串
        common.putVoNullStringToEmptyString(dataList);
        PageInfo<GroupFileVo> pageInfo = new PageInfo<>(dataList);
        TableResultResponse<GroupFileVo> res = new TableResultResponse<GroupFileVo>(
                pageInfo.getPageSize(),
                pageInfo.getPageNum(),
                pageInfo.getPages(),
                pageInfo.getTotal(),
                pageInfo.getList()
        );
        return res;
    }

    /**
     * 查询群内通过审批文件信息
     * @param groupId
     * @param page
     * @param size
     * @param userId
     * @return
     * @throws Exception
     */
    @Override
    public TableResultResponse<GroupFileVo> groupFileListByPass(String groupId,String userId, int page, int size) throws Exception {
        if (StringUtil.isEmpty(groupId)) throw new NullPointerException("id is null");
        PageHelper.startPage(page, size);
        List<GroupFileVo> dataList =this.zzGroupFileDao.groupFileListByPass(groupId);
        //null的String类型属性转换空字符串
        common.putVoNullStringToEmptyString(dataList);
        PageInfo<GroupFileVo> pageInfo = new PageInfo<>(dataList);
        TableResultResponse<GroupFileVo> res = new TableResultResponse<GroupFileVo>(
                pageInfo.getPageSize(),
                pageInfo.getPageNum(),
                pageInfo.getPages(),
                pageInfo.getTotal(),
                pageInfo.getList()
        );
        return res;
    }

    @Override
    public Long groupFileListTotal(String id) throws Exception {
        return this.zzGroupFileDao.groupFileListTotal(id);
    }
    /**
     * 获取上传附件大小（数据库统计）
     *
     * @param queryType 查询类型0天（默认），1月，2年
     * @param queryDate 查询时间
     * @param returnUnit 返回结果单位  0 M（默认），1 G，2 T
     * @return 文件大小：单位兆
     */
    public String getGroupChatFileSizeByDB(String queryType, String queryDate, String returnUnit){
        String res="";
        String dateFmt="";
        long divide=1L;
        if(queryType.equals("0")){
            dateFmt="yyyy-mm-dd";
        }else if(queryType.equals("1")){
            dateFmt="yyyy-mm";
        }else{
            dateFmt="yyyy";
        }
        if(returnUnit.equals("0")){
            divide=1024*1024L;
        }else if(returnUnit.equals("1")){
            divide=1024*1024*1024L;
        }else{
            divide=1024*1024*1024*1024L;
        }
        double sizes = this.zzGroupFileDao.queryFileSize(dateFmt,queryDate,divide);
        res=String.valueOf(common.formatDouble2(sizes));
        return res;
    }
    /**
     * 获取上传附件区间段情况（数据库统计）
     *
     * @param queryType 查询类型0天（默认），1月，2年
     * @param queryDateBegin 查询时间开始
     * @param queryDateEnd 查询时间结束
     * @param returnUnit 返回结果单位  0 M（默认），1 G，2 T
     * @return 文件去区间段大小
     */
    public List<Map<String,String>> getGroupChatFileSizeRangeByDB(String queryType, String queryDateBegin, String queryDateEnd, String returnUnit) throws Exception{
        List<Map<String,String>> res=new ArrayList<>();
        String dateFmt="";
        long divide=1L;
        if(queryType.equals("0")){
            dateFmt="yyyy-mm-dd";
        }else if(queryType.equals("1")){
            dateFmt="yyyy-mm";
        }else{
            dateFmt="yyyy";
        }
        if(returnUnit.equals("0")){
            divide=1024*1024L;
        }else if(returnUnit.equals("1")){
            divide=1024*1024*1024L;
        }else{
            divide=1024*1024*1024*1024L;
        }
        List<Map> data = this.zzGroupFileDao.queryFileSizeRange(dateFmt,queryDateBegin,queryDateEnd,divide);
        for(Map<String,Object> temp : data){
            String date = (String)temp.get("DATES");
            String size = String.valueOf(common.formatDouble2(((BigDecimal)temp.get("SIZES")).doubleValue()));
            Map<String,String> map=new HashMap<>();
            map.put("date",date);
            map.put("size",size);
            res.add(map);
        }
        return res;
    }

    //文件数据库信息补全
    public int fileRecord(ZzUploadFile zzUploadFile) throws Exception{
        //更新文件上传系统的文件属性 0代表是研讨服务
        //ObjectRestResponse objectRestResponse = iFileUploadService.fileUpdate(zzUploadFile.getFileId(),zzUploadFile.getLevels(),"0");
        //return this.zzGroupFileDao.fileRecord(zzUploadFile);
        return this.zzGroupFileDao.fileUpdate(zzUploadFile);

    }
    //文件监控查询
    //参数说明：page 页码 size 每页几条 userName上传用户名称 dateBegin、dateEnd上传时间开始结束 isGroup 是否群主1是0否
    //fileName文件名称 level密级
    public TableResultResponse<FileMonitoringVO> fileMonitoring(Map<String,Object> params) throws Exception{
        int page=Integer.valueOf(common.nulToEmptyString(params.get("page")));
        int size=Integer.valueOf(common.nulToEmptyString(params.get("size")));
        PageHelper.startPage(page, size);
        List<FileMonitoringVO> dataList =this.zzGroupFileDao.fileMonitoring(params);
        //null的String类型属性转换空字符串
        common.putVoNullStringToEmptyString(dataList);
        PageInfo<FileMonitoringVO> pageInfo = new PageInfo<>(dataList);
        TableResultResponse<FileMonitoringVO> res = new TableResultResponse<FileMonitoringVO>(
                pageInfo.getPageSize(),
                pageInfo.getPageNum(),
                pageInfo.getPages(),
                pageInfo.getTotal(),
                pageInfo.getList()
        );
        return res;
    }
    //设置文件审计标记 参数格式fileId,approveFlg;fileId,approveFlg;fileId,approveFlg;fileId,approveFlg
    //组内分割用逗号，第一个表示文件id，第二个表示审计标记；组间分割用分号
    //例如 adcssdsf,1;dsadgeggsd,0;13353ddeww,1 表示传递了三个文件，分别把它们审计标记改成通过，不通过，通过

    public int setFileApproveFLg(String files,String userId) throws Exception{
        List<Map<String,String>> params = new ArrayList<>();
        List<String> fileArr = common.stringToList(files,";");
        for(String temp:fileArr){
            String[] fileParam = temp.split(",",-1);
            if(fileParam[0]==null||fileParam[0].equals("")){
                continue;
            }
            Map<String,String> singleFile = new HashMap<>();
            singleFile.put("fileId",fileParam[0]);
            singleFile.put("approveFlg",(fileParam[1]==null||fileParam[1].equals(""))?"1":fileParam[1]);
            singleFile.put("updator",userId);
            params.add(singleFile);
        }
        return this.zzGroupFileDao.setFileApproveFLg(params);
    }
}