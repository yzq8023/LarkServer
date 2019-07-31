package com.workhub.z.servicechat.controller;

import com.github.hollykunge.security.common.msg.ObjectRestResponse;
import com.workhub.z.servicechat.config.EncryptionAndDeciphering;
import com.workhub.z.servicechat.config.common;
import com.workhub.z.servicechat.entity.ZzGroupFile;
import com.workhub.z.servicechat.entity.ZzUploadFile;
import com.workhub.z.servicechat.service.ZzFileManageService;
import com.workhub.z.servicechat.service.ZzGroupFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URLDecoder;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 附件上传下载等相关功能控制层
 *
 * @author zhuqz
 * @since 2019-06-04 10:22:34
 */
@RestController
@RequestMapping("/zzFileManage")
public class ZzFileManageController {
    private static Logger log = LoggerFactory.getLogger(ZzFileManageController.class);
    @Resource
    private ZzFileManageService zzFileManageService;
    @Resource
    private ZzGroupFileService zzGroupFileService;
    @Autowired
    private HttpServletRequest request;

    /*@RequestMapping("/singleFileUpload")
    @ResponseBody
    //上传
    public ObjectRestResponse singleFileUpload(@RequestParam("file") MultipartFile file) {
        //System.out.println("===================================================file upload=============================================================");
        String res;//0附件是空 1成功 -1上传失败 -2数据库错误
        ObjectRestResponse obj = new ObjectRestResponse();
        obj.rel(true);
        obj.msg("200");
        obj.data("成功");
        if (Objects.isNull(file) || file.isEmpty()) {
            obj.rel(false);
            obj.data("文件是空");
            return  obj;
        }
        try {
            Map<String, String> uplodaRes = zzFileManageService.singleFileUpload(file);
            res = uplodaRes.get("res");
            if (res.equals("1")) {//如果上传成功，入库记录
                ZzGroupFile zzGroupFile = new ZzGroupFile();
                zzGroupFile.setFileId(uplodaRes.get("file_id"));
                zzGroupFile.setCreator("登陆人id_测试");//TODO
                zzGroupFile.setCreateTime(new Date());
                zzGroupFile.setSizes(Double.parseDouble(uplodaRes.get("file_size")));
                zzGroupFile.setFileName(uplodaRes.get("file_upload_name"));
                zzGroupFile.setPath(uplodaRes.get("file_path"));
                zzGroupFile.setFileExt("");
                zzGroupFile.setFileType("");
                zzGroupFile.setReadPath("");
                zzGroupFile.setUpdator("登陆人id_测试");
                zzGroupFile.setUpdateTime(new Date());
                zzGroupFile.setGroupId("");
                zzGroupFile.setLevels("");
                try {
                    zzGroupFileService.insert(zzGroupFile);
                } catch (Exception e) {
                    obj.rel(false);
                    obj.data("数据库错误");
                    e.printStackTrace();
                    res = "-2";
                    //如果上传失败，那么删除已经上传的附件
                    zzFileManageService.delUploadFile(uplodaRes.get("file_path") + "/" + uplodaRes.get("file_real_name"));
                    return obj;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            obj.rel(false);
            obj.data("操作错误");
            res = "-1";
        }
        return obj;
    }*/
    @RequestMapping("/singleFileUpload")
    @ResponseBody
    //上传
    //上传 成功msg=200，失败=500；
    //     成功rel=true，失败=false
     //    成功data=返回实体，失败 ：堆栈错误打印
    public ObjectRestResponse singleFileUpload(@RequestParam("file") MultipartFile file) {
        //System.out.println("===================================================file upload=============================================================");
        ObjectRestResponse objectRestResponse = new ObjectRestResponse();
        objectRestResponse.rel(true);
        objectRestResponse.msg("200");
        ZzGroupFile zzGroupFile = new ZzGroupFile();
        if (Objects.isNull(file) || file.isEmpty()) {
            objectRestResponse.rel(false);
            objectRestResponse.msg("500");
            throw new NullPointerException("上传附件是空");
        }
        try {
            Map<String, String> uplodaRes = zzFileManageService.singleFileUpload(file);
            String res = uplodaRes.get("res");
            if (res.equals("1")) {//如果上传成功，入库记录
                zzGroupFile.setFileId(uplodaRes.get("file_id"));
                //zzGroupFile.setCreator("登陆人id_测试");//TODO
                String userId = common.nulToEmptyString(request.getHeader("userId"));
                String userName = URLDecoder.decode(common.nulToEmptyString(request.getHeader("userName")),"UTF-8");
                zzGroupFile.setCreator((userId==null)?"":userId);//TODO
                zzGroupFile.setCreatorName((userName==null)?"":userName);//TODO
                zzGroupFile.setCreateTime(new Date());
                zzGroupFile.setSizes(Double.parseDouble(uplodaRes.get("file_size")));
                zzGroupFile.setFileName(uplodaRes.get("file_upload_name"));
                zzGroupFile.setPath(uplodaRes.get("file_path"));
                zzGroupFile.setFileExt(uplodaRes.get("file_ext"));
                zzGroupFile.setFileType(uplodaRes.get("file_type"));
                zzGroupFile.setReadPath("");
                //zzGroupFile.setUpdator("登陆人id_测试");
                //zzGroupFile.setUpdateTime(new Date());
                zzGroupFile.setGroupId("");
                zzGroupFile.setLevels("");
                try {
                    zzGroupFileService.insert(zzGroupFile);
                    //int i=1/0;
                    objectRestResponse.data(zzGroupFile);
                } catch (Exception e) {
                    e.printStackTrace();
                    //如果上传失败，那么删除已经上传的附件
                    zzFileManageService.delUploadFile(uplodaRes.get("file_path"));
                    zzGroupFile=null;
                    objectRestResponse.rel(false);
                    objectRestResponse.msg("500");
                    objectRestResponse.data("上传失败："+ common.getExceptionMessage(e));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            objectRestResponse.rel(false);
            objectRestResponse.msg("500");
            objectRestResponse.data("上传失败："+ common.getExceptionMessage(e));
        }
        return objectRestResponse;
    }
    @GetMapping("/downloadFile")
    //下载 1成功 -1 失败 0 文件不存在
    public void downloadFile(HttpServletRequest request, HttpServletResponse response) {
        String fileId = request.getParameter("fileId");
        request.setAttribute("resCode","1");
        request.setAttribute("msg","下载成功");
/*

        try {
            int i=1/0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        String qqqqqq = "aaa";
*/

        if (fileId == null || "".equals(fileId)) {
            request.setAttribute("resCode","0");
            request.setAttribute("msg","附件id是空");
            return ;
        }
        ZzGroupFile zzGroupFile = zzGroupFileService.queryById(fileId);
        if (zzGroupFile == null) {
            request.setAttribute("resCode","0");
            request.setAttribute("msg","附件不存在");
            return ;
        }
        String fileName = zzGroupFile.getFileName();//下载名称
        String fileExt = zzGroupFile.getFileExt();//后缀
        if(!"".equals(fileExt)){
            fileName=fileName+"."+fileExt;
        }
        String filePath = zzGroupFile.getPath();//文件路径
        try {
            response = zzFileManageService.downloadFile(response, filePath, fileName);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("resCode","-1");
            request.setAttribute("msg","下载出错");
            return ;
        }
    }
    @PostMapping ("/fileDelete")
    //删除文件 1成功 -1 失败 0 文件不存在
    public ObjectRestResponse fileDelete(@RequestParam("fileId") String fileId) {
        ObjectRestResponse obj = new ObjectRestResponse();
        obj.rel(true);
        obj.msg("200");
        obj.data("成功");
        if (fileId == null || "".equals(fileId)) {
            obj.rel(false);
            obj.data("附件id为空");
            return  obj;
        }
        ZzGroupFile zzGroupFile = zzGroupFileService.queryById(fileId);
        //删除记录
        this.zzGroupFileService.deleteById(fileId);
        try {
            //删除文件
            zzFileManageService.delUploadFile(zzGroupFile.getPath());
        } catch (Exception e) {
            e.printStackTrace();
            obj.rel(false);
            obj.data("操作出错");
        }
        return obj;
    }

    @PostMapping ("/fileRecord")
    //文件更新（前端上传后，点击发送触发文件信息补全）
    public ObjectRestResponse fileRecord(@RequestParam ZzUploadFile zzUploadFile) {
        ObjectRestResponse obj = new ObjectRestResponse();
        obj.rel(true);
        obj.msg("200");
        obj.data("成功");
        try {
            int i =this.zzGroupFileService.fileRecord(zzUploadFile);
        } catch (Exception e) {

            e.printStackTrace();
            obj.rel(false);
            obj.data("操作出错");
        }
        return obj;
    }

    @RequestMapping(value = "/getFileImageStream",produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public BufferedImage getFileImageStream(String fileId) throws IOException {
        ZzGroupFile zzGroupFile = zzGroupFileService.queryById(fileId);
        try (InputStream is = new FileInputStream(zzGroupFile.getPath())) {
            return ImageIO.read(is);
        }
    }
    @RequestMapping("/GetFile")
    public void getFile(HttpServletRequest request , HttpServletResponse response) throws IOException {
        InputStream in =null;
        OutputStream outputStream = null;
        File newFile = null;
        try {
            //读取路径下面的文件
            String fileId = request.getParameter("fileId");
            ZzGroupFile zzGroupFile = zzGroupFileService.queryById(fileId);
            //读取路径下面的文件
            if(zzGroupFile == null) return;
            File picFile = null;
            //根据路径获取文件
            picFile = new File(zzGroupFile.getPath());
            String newFilePath="";
            if(picFile.exists()){//解密
                newFilePath= EncryptionAndDeciphering.decipherFile(picFile);
            }
            newFile = new File(newFilePath);
            //获取文件后缀名格式
            String ext = ((zzGroupFile.getFileExt()==null)?"":zzGroupFile.getFileExt());
            //判断图片格式,设置相应的输出文件格式
            if(ext.equals("jpg")){
                response.setContentType("image/jpeg");
            }else if(ext.equals("JPG")){
                response.setContentType("image/jpeg");
            }else if(ext.equals("png")){
                response.setContentType("image/png");
            }else if(ext.equals("PNG")){
                response.setContentType("image/png");
            }
            //读取指定路径下面的文件
             in = new FileInputStream(newFile);
             outputStream = new BufferedOutputStream(response.getOutputStream());
            //创建存放文件内容的数组
            byte[] buff =new byte[1024];
            //所读取的内容使用n来接收
            int n;
            //当没有读取完时,继续读取,循环
            while((n=in.read(buff))!=-1){
                //将字节数组的数据全部写入到输出流中

                outputStream.write(buff,0,n);
            }
            //强制将缓存区的数据进行输出
            outputStream.flush();
            //int i=1/0;
        }catch (Exception e){
            log.error(common.getExceptionMessage(e));
        }finally {
            //关流
            if(outputStream!=null){
                outputStream.close();
            }
            if(in!=null){
                in.close();
            }
            //解密文件删除
            if(newFile!=null && newFile.exists()){
                newFile.delete();
            }
        }

    }
    @GetMapping("/getGroupChatFileSizeByDB")
    //查询附件大小（数据库统计）
    //queryType 查询类型 0 天(默认)，1 月，2 年
    //returnUnit 单位 0 M(默认)，1 G，2 T
    //queryDate 查询日期 天 2019-01-01 ，月 2019-01，年 2019
    public String getGroupChatFileSizeByDB(@RequestParam("queryType") String queryType,@RequestParam("queryDate") String queryDate,@RequestParam("unit") String returnUnit) {
        String res = "";//-1接口报错
        try {
            res = zzGroupFileService.getGroupChatFileSizeByDB((queryType==null||queryType.equals(""))?"0":queryType,queryDate,(returnUnit==null||returnUnit.equals(""))?"0":returnUnit);
        }catch (Exception e){
            e.printStackTrace();
            res = "-1";
        }

        return res;
    }

    @GetMapping("/getGroupChatFileSizeRangeByDB")
    //查询区间附件大小（数据库统计）
    //queryType 查询类型 0 天(默认)，1 月，2 年
    //returnUnit 单位 0 M(默认)，1 G，2 T
    //queryDateBegin 查询日期开始 天 2019-01-01 ，月 2019-01，年 2019
    //queryDateEnd 查询日期结束 天 2019-01-02 ，月 2019-02，年 2020
    public List<Map<String,String>> getGroupChatFileSizeRangeByDB(@RequestParam("queryType") String queryType, @RequestParam("queryDateBegin") String queryDateBegin, @RequestParam("queryDateEnd") String queryDateEnd, @RequestParam("unit") String returnUnit) {
        List<Map<String,String>> res=null;
        try {
            res = zzGroupFileService.getGroupChatFileSizeRangeByDB((queryType==null||queryType.equals(""))?"0":queryType,queryDateBegin,queryDateEnd,(returnUnit==null||returnUnit.equals(""))?"0":returnUnit);
        }catch (Exception e){
            e.printStackTrace();
        }

        return res;
    }
}
