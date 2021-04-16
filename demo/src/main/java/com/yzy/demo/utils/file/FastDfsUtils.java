package com.yzy.demo.utils.file;

import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.domain.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.service.DefaultGenerateStorageClient;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.yzy.demo.config.SpringContextUtil;
import com.yzy.demo.exception.ExceptionEnum;
import com.yzy.demo.exception.throwtype.MyException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.io.*;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;

/**
 * FastDfs 工具类
 * author : yangzhenyu
 * */
@Component
@ConditionalOnExpression(("#{null !=(environment['config.file.type']) && 'fastdfs' ==(environment['config.file.type'])}"))
public class FastDfsUtils implements FileBase{

    private static Logger log = LoggerFactory.getLogger(FileBase.class);
    @Value("${config.file.fastdfs.groupName:group1}")
    private String configGroupName;
    @Resource
    private FastFileStorageClient storageClient ;
    /**
     * 基本文件存储客户端操作
     *
     */
    public static DefaultGenerateStorageClient getGenerateStorageClient () {
        return SpringContextUtil.getBean("defaultGenerateStorageClient");
    }

    //将字节流写到磁盘生成文件
    private void saveFile(byte[] b, String path, String fileName) {
        File file = new File(path+fileName);
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream= new FileOutputStream(file);

            fileOutputStream.write(b);
        } catch (FileNotFoundException e) {
            log.error("将字节流写到磁盘生成文件失败：", e);
        } catch (IOException e) {
            log.error("将字节流写到磁盘生成文件失败：", e);
        }finally{
            if(fileOutputStream!=null){
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    log.error("流关闭失败：", e);
                }
            }
        }
    }

    /**
     * 下载
     * */
    public  void download(String key,String type) {
        String fileName = UUID.randomUUID().toString();
        try{
            DownloadByteArray downloadFileStream = new DownloadByteArray();
            byte[] result = FastDfsUtils.getGenerateStorageClient().downloadFile(configGroupName, key, downloadFileStream);
            if(result !=null){
                saveFile(result, "D:\\", fileName+"."+type);
            }
        }catch(Exception e){
            log.error("fastdfs 下载失败：", e);
            throw new MyException(ExceptionEnum.DOWNLOAD);
        }
    }

    /**
     * 上传图片
     * */
    public  String upload(MultipartFile file) {
        InputStream is = null;
        String fileExtName = null;
        try {
            Assert.notNull(file, "上传文件不能为空");
            is = file.getInputStream();
            String originalFilename = file.getOriginalFilename();
            fileExtName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            Assert.hasLength(fileExtName, "文件后缀不能为空");
            DefaultGenerateStorageClient client = FastDfsUtils.getGenerateStorageClient();
            StorePath storePath = client.uploadFile(configGroupName, is, is.available(), fileExtName);
            return storePath.getFullPath();
        } catch (Exception e) {
            log.error("fastdfs 上传失败：", e);
            throw new MyException(ExceptionEnum.UPLOAD);
        } finally {
            try {
                if (null != is) {
                    is.close();
                }
            } catch (IOException e) {
                log.error("fastDfs上传关流出错", e);
            }
        }
    }

    /**
     * 删除
     * */
    public void delete(String key) {
        if (StringUtils.isEmpty(key)) {
            log.error("【fastDFS】key为空...");
            throw new MyException(ExceptionEnum.NULL);
        }
        try {
            FastDfsUtils.getGenerateStorageClient().deleteFile(configGroupName, key);
        } catch (Exception e) {
            log.error("【fastDFS】删除失败",e);
            throw new MyException(ExceptionEnum.DELETE);
        }
    }
}
