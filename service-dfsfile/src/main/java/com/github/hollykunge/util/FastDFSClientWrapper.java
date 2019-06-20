package com.github.hollykunge.util;

import com.github.hollykunge.security.common.exception.BaseException;
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.exception.FdfsUnsupportStorePathException;
import com.github.tobato.fastdfs.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.Charset;

@Slf4j
@Component
public class FastDFSClientWrapper {
    @Value("${upload.picture.mast}")
    private String permission;

    @Autowired
    private FastFileStorageClient storageClient;


    /**
     * 上传文件
     * @param file 文件对象
     * @return 文件访问地址相对路径，如果想要访问该文件使用全路径如：http://nginxIP:80/ + 返回值
     * @throws IOException
     */
    public String uploadFile(MultipartFile file) throws IOException {
        StorePath storePath = storageClient.uploadFile(file.getInputStream(),file.getSize(), FilenameUtils.getExtension(file.getOriginalFilename()),null);
        return storePath.getFullPath();
    }

    /**
     * 将一段字符串生成一个文件上传
     * @param content 文件访问地址相对路径，如果想要访问该文件使用全路径如：http://nginxIP:80/ 返回值
     * @param fileExtension
     * @return
     */
    public String uploadFile(String content, String fileExtension) {
        byte[] buff = content.getBytes(Charset.forName("UTF-8"));
        ByteArrayInputStream stream = new ByteArrayInputStream(buff);
        StorePath storePath = storageClient.uploadFile(stream,buff.length, fileExtension,null);
        return storePath.getFullPath();
    }

    // 封装图片完整URL地址
//    private String getResAccessUrl(StorePath storePath) {
//        String fileUrl = "http://" + appConfig.getResHost()
//                + ":" + appConfig.getStoragePort() + "/" + storePath.getFullPath();
//        return fileUrl;
//    }

    /**
     * 删除文件
     * @param fileUrl 文件访问地址
     * @return
     */
    public void deleteFile(String fileUrl) {
        if (StringUtils.isEmpty(fileUrl)) {
            throw new BaseException("要删除的文件id,不能为null...");
        }
        try {
            StorePath storePath = StorePath.praseFromUrl(fileUrl);
            storageClient.deleteFile(storePath.getGroup(), storePath.getPath());
        } catch (FdfsUnsupportStorePathException e) {
            log.debug(e.getMessage());
        }
    }

    /**
     * 下载文件
     * @param fileUrl 文件url
     * @return
     */
    public byte[]  download(String fileUrl) {
        String group = fileUrl.substring(0, fileUrl.indexOf("/"));
        String path = fileUrl.substring(fileUrl.indexOf("/") + 1);
        byte[] bytes = storageClient.downloadFile(group, path, new DownloadByteArray());
        return bytes;
    }

    /**
     * 上传图片并生成缩略图
     * @param file
     * @return 文件访问地址相对路径，如果想要访问该文件使用全路径如：http://nginxIP:80/ 返回值
     * @throws IOException
     */
    public String crtThumbImage(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        if(StringUtils.isEmpty(originalFilename)){
            throw new BaseException("上传文件有错误...");
        }
        String originalName = originalFilename.substring(originalFilename.indexOf(".") + 1, originalFilename.length());
        if(permission.contains(originalName)){
            throw new BaseException("生成缩略图的图片类型不允许...");
        }
        System.out.printf(originalFilename);
        //上传图片的缩略图
        StorePath storePath = this.storageClient.uploadImageAndCrtThumbImage(file.getInputStream(),
                file.getSize(), FilenameUtils.getExtension(file.getOriginalFilename()),null);
        return storePath.getFullPath();
    }
}