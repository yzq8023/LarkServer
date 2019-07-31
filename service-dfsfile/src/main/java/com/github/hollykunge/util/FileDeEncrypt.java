package com.github.hollykunge.util;

import java.io.*;
import java.security.Key;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.spec.SecretKeySpec;

import com.github.hollykunge.comtants.FileComtants;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/***
 * Des文件加密解密
 * @author zhhongyu
 */
@Slf4j
public class FileDeEncrypt {


    public FileDeEncrypt(String keyStr) {
        genKey(keyStr);
        initCipher();
    }

    private Key key;
    /**
     * 解密密码
     */
    private Cipher cipherDecrypt;
    /**
     * 加密密码
     */
    private Cipher cipherEncrypt;


    /**
     * 加密文件的核心
     * @param file  要加密的文件
     */
    public byte[] encryptFile(byte[] file) throws IOException {
        try {
            return this.fileTranfer(file,cipherEncrypt);
        } catch (Exception e) {
            log.error("加密文件出现异常", e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }


    /***
     * 解密文件
     * @param file
     */
    public byte[] decryptFileContent(byte[] file) throws IOException {
        try {
            return this.fileTranfer(file,cipherDecrypt);
        } catch (Exception e) {
            log.error("解密文件{}出现异常", e.getMessage());
            throw e;
        }
    }
    private byte[] fileTranfer(byte[] file,Cipher cipher) throws IOException {
        InputStream is = new ByteArrayInputStream(file);
        OutputStream out = new ByteArrayOutputStream();
        CipherInputStream cis = new CipherInputStream(is, cipher);
        byte[] buffer = new byte[1024];
        int r;
        while ((r = cis.read(buffer)) > 0) {
            out.write(buffer, 0, r);
        }
        cis.close();
        is.close();
        out.close();
        return ((ByteArrayOutputStream) out).toByteArray();
    }
    private void initCipher() {
        try {
            // 加密的cipher
            cipherEncrypt = Cipher.getInstance(FileComtants.ENCRYPT_TYPE);
            cipherEncrypt.init(Cipher.ENCRYPT_MODE, key);
            // 解密的cipher
            cipherDecrypt = Cipher.getInstance(FileComtants.ENCRYPT_TYPE);
            cipherDecrypt.init(Cipher.DECRYPT_MODE, key);
        } catch (Exception e) {
            log.info("加密初始化出现异常:{}", e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 自定义一个key
     * @param keyRule
     */
    public void genKey(String keyRule) {
        // Key key = null;
        byte[] keyByte = keyRule.getBytes();
        // 创建一个空的八位数组,默认情况下为0
        byte[] byteTemp = new byte[8];
        // 将用户指定的规则转换成八位数组
        for (int i = 0; i < byteTemp.length && i < keyByte.length; i++) {
            byteTemp[i] = keyByte[i];
        }
        key = new SecretKeySpec(byteTemp, FileComtants.ENCRYPT_TYPE);
    }
}