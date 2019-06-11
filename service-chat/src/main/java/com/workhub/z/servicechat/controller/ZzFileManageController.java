package com.workhub.z.servicechat.controller;

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
    public String singleFileUpload(@RequestParam("file") MultipartFile file) {
        //System.out.println("===================================================file upload=============================================================");
        String res;//0附件是空 1成功 -1上传失败 -2数据库错误
        if (Objects.isNull(file) || file.isEmpty()) {
            return "0";
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
                    e.printStackTrace();
                    res = "-2";
                    //如果上传失败，那么删除已经上传的附件
                    zzFileManageService.delUploadFile(uplodaRes.get("file_path") + "/" + uplodaRes.get("file_real_name"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            res = "-1";
        }
        return res;
    }

    @GetMapping("/downloadFile")
    //下载 1成功 -1 失败 0 文件不存在
    public String downloadFile(HttpServletRequest request, HttpServletResponse response) {
        String fileId = request.getParameter("fileId");
        if (fileId == null || "".equals(fileId)) {
            return "0";
        }
        ZzGroupFile zzGroupFile = zzGroupFileService.queryById(fileId);
        if (zzGroupFile == null) {
            return "0";
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
            return "-1";
        }


        return "1";
    }
}
