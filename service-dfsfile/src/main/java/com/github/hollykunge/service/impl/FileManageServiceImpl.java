package com.github.hollykunge.service.impl;

import com.github.hollykunge.dao.FileManageDao;
import com.github.hollykunge.entity.FileManageInf;
import com.github.hollykunge.service.FileMangeService;
import com.github.hollykunge.util.CommonUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("fileManageService")
public class FileManageServiceImpl implements FileMangeService {
    @Resource
    FileManageDao fileManageDao;

    public FileManageInf queryById(String fileId){
       return  this.fileManageDao.queryById(fileId);
    }
    public int insert(FileManageInf fileManageInf){
        return this.fileManageDao.insert(fileManageInf);
    }
    public int update(FileManageInf fileManageInf){
        return this.fileManageDao.update(fileManageInf);
    }
    public int deleteById(String fileId){
        return this.fileManageDao.deleteById(fileId);
    }
    public int fileUpdate(String fileId,String level,String type){
        return this.fileManageDao.fileUpdate(fileId,level,type);
    }
    /**
     * 获取上传附件大小（数据库统计）
     *
     * @param queryType 查询类型0天（默认），1月，2年
     * @param queryDate 查询时间
     * @param returnUnit 返回结果单位  0 M（默认），1 G，2 T
     * @return 文件大小：单位兆
     */
    public String getFileSizeByDB(String queryType, String queryDate, String returnUnit){
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
        double sizes = this.fileManageDao.queryFileSize(dateFmt,queryDate,divide);
        res=String.valueOf(CommonUtil.formatDouble2(sizes));
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
    public List<Map<String,String>> getFileSizeRangeByDB(String queryType, String queryDateBegin, String queryDateEnd, String returnUnit)  {
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
        List<Map> data = this.fileManageDao.queryFileSizeRange(dateFmt,queryDateBegin,queryDateEnd,divide);
        for(Map<String,Object> temp : data){
            String date = (String)temp.get("DATES");
            String size = String.valueOf(CommonUtil.formatDouble2(((BigDecimal)temp.get("SIZES")).doubleValue()));
            Map<String,String> map=new HashMap<>();
            map.put("date",date);
            map.put("size",size);
            res.add(map);
        }
        return res;
    }
}