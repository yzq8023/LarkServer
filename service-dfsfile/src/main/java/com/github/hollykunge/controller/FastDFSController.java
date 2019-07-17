package com.github.hollykunge.controller;

import com.github.hollykunge.security.common.msg.ObjectRestResponse;
import com.github.hollykunge.security.common.rest.BaseController;
import com.github.hollykunge.util.FastDFSClientWrapper;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;

/**
 * file文件接口
 * @author zhhongyu
 * @since 2019-06-18
 */
@RestController
@RequestMapping("file")
public class FastDFSController {
    @Autowired
    private FastDFSClientWrapper dfsClient;

    /**
     * 上传接口
     * @param file file文件
     * @return 文件访问路径
     * @throws Exception
     */
    @PostMapping("/upload")
    @ResponseBody
    public ObjectRestResponse<String> upload(@RequestParam("file") MultipartFile file) throws Exception {
        String imgUrl = dfsClient.uploadFile(file);
        return new ObjectRestResponse<>().data(imgUrl).rel(true);
    }
    /**
     * 上传加密文件接口
     * @param file file文件
     * @return 文件访问路径
     * @throws Exception
     */
    @PostMapping("/sensitiveUpload")
    @ResponseBody
    public ObjectRestResponse<String> uploadSensitiveFile(@RequestParam("file") MultipartFile file) throws Exception {
        String imgUrl = dfsClient.uploadSensitiveFile(file);
        return new ObjectRestResponse<>().data(imgUrl).rel(true);
    }

    /**
     * 删除文件接口
     * @param file 有效的路径样式为(group/path) 或者
     *       (http://ip/group/path),路径地址必须包含group
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "",method = RequestMethod.DELETE)
    @ResponseBody
    public ObjectRestResponse<Boolean> remove(@RequestParam String file) throws Exception {
        dfsClient.deleteFile(file);
        return new ObjectRestResponse<>().rel(true);
    }

    /**
     * 文件下载
     * @param fileUrl  url 开头从组名开始
     * @param response
     * @throws Exception
     */
    @GetMapping("/download")
    public void  download(@RequestParam String fileUrl,@RequestParam String fileName, HttpServletResponse response) throws Exception{
        byte[] data = dfsClient.download(fileUrl);
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        ServletOutputStream outputStream = response.getOutputStream();
        IOUtils.write(data, outputStream);
    }

    /**
     * 下载加密文件
     * @param fileUrl
     * @param fileName
     * @param response
     * @throws Exception
     */
    @GetMapping("/sensitiveDownload")
    public void  downloadSensitiveFile(@RequestParam String fileUrl,@RequestParam String fileName, HttpServletResponse response) throws Exception{
        byte[] data = dfsClient.downloadSensitiveFile(fileUrl);
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        ServletOutputStream outputStream = response.getOutputStream();
        IOUtils.write(data, outputStream);
    }

    @PostMapping("/thumbImage")
    @ResponseBody
    public ObjectRestResponse<String> crtThumbImage(@RequestParam("file") MultipartFile file) throws Exception {
        String imgUrl = dfsClient.crtThumbImage(file);
        return new ObjectRestResponse<>().data(imgUrl).rel(true);
    }
}