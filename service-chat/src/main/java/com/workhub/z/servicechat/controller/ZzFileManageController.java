package com.workhub.z.servicechat.controller;

import com.github.hollykunge.security.common.msg.ObjectRestResponse;
import com.workhub.z.servicechat.entity.ZzGroupFile;
import com.workhub.z.servicechat.service.ZzFileManageService;
import com.workhub.z.servicechat.service.ZzGroupFileService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
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
    @Resource
    private ZzFileManageService zzFileManageService;
    @Resource
    private ZzGroupFileService zzGroupFileService;

    @RequestMapping("/singleFileUpload")
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
    }

    @GetMapping("/downloadFile")
    //下载 1成功 -1 失败 0 文件不存在
    public ObjectRestResponse downloadFile(HttpServletRequest request, HttpServletResponse response) {
        String fileId = request.getParameter("fileId");
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
        if (zzGroupFile == null) {
            obj.rel(false);
            obj.data("附件不存在");
            return  obj;
        }
        String fileName = zzGroupFile.getFileName();//下载名称
        String filePath = zzGroupFile.getPath();//文件路径
        String suffix = "";
        if (fileName.indexOf(".") != -1) {
            suffix = fileName.substring(fileName.lastIndexOf("."));
        }
        String realFileName = fileId + suffix;
        if (filePath == null) {
            filePath = realFileName;
        } else {
            filePath = filePath + "/" + realFileName;
        }

        try {
            response = zzFileManageService.downloadFile(response, filePath, fileName);
        } catch (Exception e) {
            e.printStackTrace();
            obj.rel(false);
            obj.data("操作出错");
            return  obj;
        }


        return obj;
    }
}
