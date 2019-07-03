package com.workhub.z.servicechat.service.impl;

import com.github.hollykunge.security.common.msg.TableResultResponse;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.workhub.z.servicechat.config.DataDictionary;
import com.workhub.z.servicechat.config.ExcelUtil;
import com.workhub.z.servicechat.config.RandomId;
import com.workhub.z.servicechat.config.common;
import com.workhub.z.servicechat.dao.ZzDictionaryWordsDao;
import com.workhub.z.servicechat.entity.ZzDictionaryWords;
import com.workhub.z.servicechat.service.ZzDictionaryWordsService;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.*;

import static com.workhub.z.servicechat.config.common.putEntityNullToEmptyString;

/**
 * 字典词汇表(ZzDictionaryWords)表服务实现类
 *
 * @author makejava
 * @since 2019-05-17 14:56:57
 */
@Service("zzDictionaryWordsService")
public class ZzDictionaryWordsServiceImpl implements ZzDictionaryWordsService {
    private static Logger log = LoggerFactory.getLogger(ZzDictionaryWordsServiceImpl.class);
    @Resource
    private ZzDictionaryWordsDao zzDictionaryWordsDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public ZzDictionaryWords queryById(String id) {
        //return this.zzDictionaryWordsDao.queryById(id);
       /* ZzDictionaryWords entity = new ZzDictionaryWords();
        entity.setId(id);
        return  super.selectOne(entity);*/
       ZzDictionaryWords zzDictionaryWords = this.zzDictionaryWordsDao.queryById(id);
        try {
            common.putVoNullStringToEmptyString(zzDictionaryWords);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(common.getExceptionMessage(e));
        }
        return zzDictionaryWords;
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<ZzDictionaryWords> queryAllByLimit(int offset, int limit) {
        return this.zzDictionaryWordsDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param zzDictionaryWords 实例对象
     * @return 实例对象 0表示数据已经存在，无法重复添加
     */

    public int insert(ZzDictionaryWords zzDictionaryWords) {
        try {
            putEntityNullToEmptyString(zzDictionaryWords);
        } catch (Exception e) {
            e.printStackTrace();
        }

        long lo  = this.zzDictionaryWordsDao.selcount("",zzDictionaryWords.getWordName());
        if(lo >0){//如果已经存在记录
            return 0;
        }
         int insert=this.zzDictionaryWordsDao.insert(zzDictionaryWords);
        return insert;
        //super.insert(zzDictionaryWords);
    }

    /*@Override
    protected String getPageName() {
        return null;
    }
*/
    /**
     * 修改数据
     *
     * @param zzDictionaryWords 实例对象
     * @return 实例对象
     */
    @Override
    public int update(ZzDictionaryWords zzDictionaryWords) {
        long insert = this.zzDictionaryWordsDao.selcount(zzDictionaryWords.getId(),zzDictionaryWords.getWordName());
        if(insert>0){//如果已经存在记录
            return 0;
        }
        if(zzDictionaryWords.getWordType().equals("1")){//涉密词汇
            zzDictionaryWords.setReplaceWord("");//替换词清空
        }
        if(zzDictionaryWords.getWordType().equals("2")){//敏感词汇
            zzDictionaryWords.setWordCode("");
        }
        int update = this.zzDictionaryWordsDao.update(zzDictionaryWords);
        return  update;
        //return update;
        //super.updateById(zzDictionaryWords);
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public void deleteById(String id) {
        //return this.zzDictionaryWordsDao.deleteById(id) > 0;
        /*ZzDictionaryWords entity = new ZzDictionaryWords();
        entity.setId(id);
        super.delete(entity);*/
        this.zzDictionaryWordsDao.deleteById(id);
    }


    @Override
    public String sensitiveIndex(String txt) {
        ZzDictionaryWords zzDictionaryWords = new ZzDictionaryWords();
        zzDictionaryWords.setWordType("SENSITIVE");
        zzDictionaryWords.setIsUse("1");
        List<ZzDictionaryWords> zzDictionaryWordsList = this.zzDictionaryWordsDao.queryAll(zzDictionaryWords);
        if (null == zzDictionaryWordsList && zzDictionaryWordsList.isEmpty())return txt;
        return common.sensitiveSearch(txt,zzDictionaryWordsList);
    }

    @Override
    public String confidentialIndex(String txt) {
        ZzDictionaryWords zzDictionaryWords = new ZzDictionaryWords();
        zzDictionaryWords.setWordType("CONFIDENTIAL");
        zzDictionaryWords.setIsUse("1");
        List<ZzDictionaryWords> zzDictionaryWordsList = this.zzDictionaryWordsDao.queryAll(zzDictionaryWords);
        if (null == zzDictionaryWordsList && zzDictionaryWordsList.isEmpty())return "";

//        Set<String> strSet = new HashSet<String>();
//        zzDictionaryWordsList.stream().forEach(zzDictionaryWordsListfor ->{
//            strSet.add(zzDictionaryWordsListfor.getWordName());
//        });
        return common.stringSearch(txt, zzDictionaryWordsList);
    }
    //导入敏感词汇
    //如果模板内有重复数据，只会导入第一条校验通过的数据
    //如果数据库已经存在词汇，那么不会做任何操作
    public String importDictionaryWords(MultipartFile file, String userId) throws Exception{
        Workbook wb = ExcelUtil.getWb(file);
        Map<Integer,Object> headerMap = new HashMap<>();
        Map<Integer, Map<Integer,Object>> datamap = new HashMap<>();
        StringBuilder info = new StringBuilder();
        List<ZzDictionaryWords> dbList=new ArrayList<>();
        List<String> wordsRepeat = new ArrayList<>();//重复校验

        //第1、2行是标题头
        int titleRowNum=2;
        try {
            headerMap=ExcelUtil.readExcelSingleRow(wb,1);
            if(!(headerMap.get(0).toString()).equals("词汇类型")){
                info.append("敏感词汇导入：模板错误"+"/r/n");
               log.error("敏感词汇导入：模板错误");
               throw new Exception("模板错误");
            }
            if(!(headerMap.get(1).toString()).equals("词汇编码")){
                info.append("敏感词汇导入：模板错误"+"/r/n");
                log.error("敏感词汇导入：模板错误");
                throw new Exception("模板错误");
            }
            if(!(headerMap.get(2).toString()).equals("词汇名称")){
                info.append("敏感词汇导入：模板错误"+"/r/n");
                log.error("敏感词汇导入：模板错误");
                throw new Exception("模板错误");
            }
            if(!(headerMap.get(3).toString()).equals("替换词汇")){
                info.append("敏感词汇导入：模板错误"+"/r/n");
                log.error("敏感词汇导入：模板错误");
                throw new Exception("模板错误");
            }

            datamap = ExcelUtil.readExcelContentz(wb,titleRowNum);
        } catch (Exception e) {
            log.error(common.getExceptionMessage(e));
            e.printStackTrace();
            info.append(common.getExceptionMessage(e));
        }
        //excel数据存在map里，map.get(0).get(0)为excel(实际存放数据区域，比如标题头不算工作区)第1行第1列的值，此处可对数据进行处理
        if(datamap!=null && datamap.size()!=0){
            //System.out.println(datamap.get(0).get(0));
            Set<Integer> rowSetKey=datamap.keySet();
            Iterator<Integer> itRow = rowSetKey.iterator();
            while (itRow.hasNext()) {
                boolean validFlg=true;//本行校验
                int rowN = itRow.next();//行号
                Map<Integer,Object> rowD=datamap.get(rowN);//行数据
                String wordType = (rowD.get(2)==null)?"":rowD.get(0).toString();//词汇类型
                String wordCode = (rowD.get(2)==null)?"":rowD.get(1).toString();//词汇编码
                String wordName = (rowD.get(2)==null)?"":rowD.get(2).toString();//词汇名称
                String wordRep = (rowD.get(2)==null)?"":rowD.get(3).toString();//替换词汇
                if(wordName.equals("")){
                    info.append("第"+(rowN+titleRowNum)+"行词汇名称没有填写"+"/r/n");
                    validFlg=false;
                }
                if(wordType.equals("")){
                    info.append("第"+(rowN+titleRowNum)+"行词汇类型没有填写"+"/r/n");
                    validFlg=false;
                }
                if(wordType.equals("涉密") && wordCode.equals("")){
                    info.append("第"+(rowN+titleRowNum)+"行词汇类型是涉密，需要填写词汇编码"+"/r/n");
                    validFlg=false;
                }
                if(wordType.equals("敏感") && wordRep.equals("")){
                    info.append("第"+(rowN+titleRowNum)+"行词汇类型是敏感，需要填写替换词汇"+"/r/n");
                    validFlg=false;
                }
                //重复校验
                if(!wordName.equals("")){
                     if(wordsRepeat.contains(wordName)){
                         info.append("第"+(rowN+titleRowNum)+"行词汇["+wordName+"]重复"+"/r/n");
                         validFlg=false;
                     }
                }
                if(!validFlg){//如果校验不通过，跳转下一行
                    continue;
                }
                wordsRepeat.add(wordName);
                ZzDictionaryWords zzDictionaryWords = new ZzDictionaryWords();
                zzDictionaryWords.setId(RandomId.getUUID());
                zzDictionaryWords.setWordType(DataDictionary.getWordTypeDic().get(wordType));
                zzDictionaryWords.setCreateTime(new Date());
                zzDictionaryWords.setCreateUser(userId);
                zzDictionaryWords.setReplaceWord(wordRep);
                zzDictionaryWords.setWordCode(DataDictionary.getWordCodeDic().get(wordCode));
                zzDictionaryWords.setWordName(wordName);
                /*//测试：
                DataDictionary.getWordTypeDic().put("new","33333");
                DataDictionary.getWordTypeDic().get("new");*/
                if(wordType.equals("涉密")){//如果是涉密词汇，去掉替换词
                    zzDictionaryWords.setReplaceWord("");
                }
                if(wordType.equals("敏感")){//如果是敏感词汇，去掉词汇编码
                    zzDictionaryWords.setWordCode("");
                }
                dbList.add(zzDictionaryWords);
            }
        }else{
            info.append("excel文件没有数据");
        }
        if(dbList.size()!=0){
            for (int i = 0; i < dbList.size() ; i++) {
                int j = this.zzDictionaryWordsDao.importData(dbList.get(i));
            }
        }
        //int x=this.zzDictionaryWordsDao.importDataList(dbList);
        if(info.length()==0){
            info.append("共导入数据："+dbList.size()+"条。");
        }else{
            info.insert(0,"共导入数据："+dbList.size()+"条。其他信息：");
        }
        return  info.toString();
    }
    //停用启用
    public  int stopUse(String id,String useFlg,String userId) throws  Exception{
             int res = this.zzDictionaryWordsDao.stopUse(id,useFlg,userId);
             return res;
    }
    //分页查询
    public TableResultResponse<ZzDictionaryWords> query(int page, int size, String type, String code, String name, String replace,String isUse) throws  Exception{
        PageHelper.startPage(page, size);
        List<ZzDictionaryWords> dataList =this.zzDictionaryWordsDao.query(type,code,name,replace,isUse);
        //null的String类型属性转换空字符串
        common.putVoNullStringToEmptyString(dataList);
        PageInfo<ZzDictionaryWords> pageInfo = new PageInfo<>(dataList);
        TableResultResponse<ZzDictionaryWords> res = new TableResultResponse<ZzDictionaryWords>(
                pageInfo.getPageSize(),
                pageInfo.getPageNum(),
                pageInfo.getPages(),
                pageInfo.getTotal(),
                pageInfo.getList()
        );
        return res;
    }

}