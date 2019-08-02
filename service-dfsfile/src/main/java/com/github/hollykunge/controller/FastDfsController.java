package com.github.hollykunge.controller;

import com.github.hollykunge.biz.FileInfoBiz;
import com.github.hollykunge.comtants.FileComtants;
import com.github.hollykunge.entity.FileInfoEntity;
import com.github.hollykunge.security.common.msg.ObjectRestResponse;
import com.github.hollykunge.security.common.rest.BaseController;
import com.github.hollykunge.security.common.vo.FileInfoVO;
import com.github.hollykunge.util.FastDFSClientWrapper;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * file文件接口
 * @author zhhongyu
 * @since 2019-06-18
 */
@RestController
@RequestMapping("file")
public class FastDfsController extends BaseController<FileInfoBiz, FileInfoEntity> {
    @Autowired
    private FastDFSClientWrapper dfsClient;


//    @PostMapping("/upload")
//    @ResponseBody
//    public ObjectRestResponse<String> upload(@RequestParam("file") MultipartFile file) throws Exception {
//        String imgUrl = dfsClient.uploadFile(file);
//        return new ObjectRestResponse<>().data(imgUrl).rel(true);
//    }
    /**
     * 上传接口
     * @param file file文件
     * @return 文件访问路径
     * @throws Exception
     */
    @PostMapping("/upload")
    @ResponseBody
    public ObjectRestResponse<FileInfoVO> upload(@RequestParam("file") MultipartFile file) throws Exception {
        FileInfoVO fileInfoVO = baseBiz.uploadFile(file);
        return new ObjectRestResponse<>().data(fileInfoVO).rel(true);
    }

//    @PostMapping("/sensitiveUpload")
//    @ResponseBody
//    public ObjectRestResponse<String> uploadSensitiveFile(@RequestParam("file") MultipartFile file) throws Exception {
//        String imgUrl = dfsClient.uploadSensitiveFile(file);
//        return new ObjectRestResponse<>().data(imgUrl).rel(true);
//    }
    /**
     * 上传加密文件接口（base64加密方式）
     * @param file file文件
     * @return 文件访问路径
     * @throws Exception
     */
    @PostMapping("/sensitiveUpload")
    @ResponseBody
    public ObjectRestResponse<String> uploadChiperSensitiveFile(@RequestParam("file") MultipartFile file) throws Exception {
        //使用base64进行加密
        FileInfoVO fileInforVO = baseBiz.uploadSensitiveFile(file, FileComtants.SENSITIVE_CIPHER_TYPE);
        return new ObjectRestResponse<>().data(fileInforVO).rel(true);
    }

    /**
     * 采用位移加密方式上传文件接口
     * @param file
     * @return
     * @throws Exception
     */
    @PostMapping("/sensitiveUpload2")
    @ResponseBody
    public ObjectRestResponse<String> uploadByteMoveSensitiveFile(@RequestParam("file") MultipartFile file) throws Exception {
        //使用base64进行加密
        FileInfoVO fileInfoVO = baseBiz.uploadSensitiveFile(file, FileComtants.SENSITIVE_BYTEMOVE_TYPE);
        return new ObjectRestResponse<>().data(fileInfoVO).rel(true);
    }

    /**
     * 删除文件接口
     * @param fileId 文件id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "delete",method = RequestMethod.DELETE)
    @ResponseBody
    public ObjectRestResponse<Boolean> removeFile(@RequestParam String fileId) throws Exception {
        baseBiz.deleteFile(fileId);
        return new ObjectRestResponse<>().rel(true);
    }

    /**
     * 文件下载(普通文件下载，没有加密)
     * @param fileId 文件id
     * @param response
     * @throws Exception
     */
    @GetMapping("/download")
    public void  download(@RequestParam String fileId, HttpServletResponse response) throws Exception{
        Map<String, Object> stringObjectMap = baseBiz.downLoadFile(fileId,FileComtants.NO_SENSITIVE_TYPE);
        String fileName = (String) stringObjectMap.get("fileName");
        byte[] data = (byte[]) stringObjectMap.get("fileByte");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        ServletOutputStream outputStream = response.getOutputStream();
        IOUtils.write(data, outputStream);
    }

    /**
     * 下载加密文件(文件流加密)
     * @param fileId
     * @param response
     * @throws Exception
     */
    @GetMapping("/sensitiveDownload")
    public void  downloadChiperSensitiveFile(@RequestParam String fileId, HttpServletResponse response) throws Exception{
        Map<String, Object> stringObjectMap = baseBiz.downLoadFile(fileId,FileComtants.SENSITIVE_CIPHER_TYPE);
        String fileName = (String) stringObjectMap.get("fileName");
        byte[] data = (byte[]) stringObjectMap.get("fileByte");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        ServletOutputStream outputStream = response.getOutputStream();
        IOUtils.write(data, outputStream);
    }

    /**
     * 下载加密文件（位移加密下载）
     * @param fileId
     * @param response
     * @throws Exception
     */
    @GetMapping("/sensitiveDownload2")
    public void  downloadByteMoveSensitiveFile(@RequestParam String fileId, HttpServletResponse response) throws Exception{
        Map<String, Object> stringObjectMap = baseBiz.downLoadFile(fileId,FileComtants.SENSITIVE_BYTEMOVE_TYPE);
        String fileName = (String) stringObjectMap.get("fileName");
        byte[] data = (byte[]) stringObjectMap.get("fileByte");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        ServletOutputStream outputStream = response.getOutputStream();
        IOUtils.write(data, outputStream);
    }

    /**
     * 图片展示接口（无加密图片）
     * @param fileId
     * @param response
     * @throws IOException
     */
    @RequestMapping("/getImage")
    public void getFile(@RequestParam String fileId  , HttpServletResponse response) throws IOException {
        baseBiz.getImg(fileId,response,FileComtants.NO_SENSITIVE_TYPE);
    }
    /**
     * 加密图片展示(文件流加密)
     * @param fileId
     * @param response
     * @throws IOException
     */
    @RequestMapping("/getSensitiveImage")
    public void getSensitiveImage(@RequestParam String fileId , HttpServletResponse response) throws IOException {
        baseBiz.getImg(fileId,response,FileComtants.SENSITIVE_CIPHER_TYPE);
    }
    /**
     * 加密图片展示（位移加密图片）
     * @param fileId
     * @param response
     * @throws IOException
     */
    @RequestMapping("/getSensitiveImage2")
    public void getSensitiveImage2(@RequestParam String fileId , HttpServletResponse response) throws IOException {
        baseBiz.getImg(fileId,response,FileComtants.SENSITIVE_BYTEMOVE_TYPE);
    }

    @PostMapping("/thumbImage")
    @ResponseBody
    public ObjectRestResponse<String> crtThumbImage(@RequestParam("file") MultipartFile file) throws Exception {
        String imgUrl = dfsClient.crtThumbImage(file);
        return new ObjectRestResponse<>().data(imgUrl).rel(true);
    }
}
