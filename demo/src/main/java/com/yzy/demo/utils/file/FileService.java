package com.yzy.demo.utils.file;

import com.yzy.demo.config.SpringContextUtil;
import com.yzy.demo.exception.ExceptionEnum;
import com.yzy.demo.exception.throwtype.MyException;
import org.springframework.web.multipart.MultipartFile;

/**
 * file 类
 * author : yangzhenyu
 * */
public class FileService {
    private static FileBase fileBase;

    /**
     * 获取操作类
     * @return
     */
    public static FileBase getFileBase() {
        if (null == fileBase) {
            fileBase = SpringContextUtil.getBean(FileBase.class);
            if (null == fileBase) {
                throw new MyException(ExceptionEnum.CONFIG);
            }
        }
        return fileBase;
    }

    /**
     * 上传文件
     *
     * @param file 文件
     * @return
     */
    public static String upload(MultipartFile file) {
        return getFileBase().upload(file);
    }

    /**
     * 上传文件删除
     * */
    public static void delete(String key) {
         getFileBase().delete(key);
    }
    /**
     * 文件下载
     * */
    public static void download( String key,String type){
        getFileBase().download(key,type);
    }
}
