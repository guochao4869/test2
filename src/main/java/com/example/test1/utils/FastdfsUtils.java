package com.example.test1.utils;

import com.example.test1.pojo.Result;
import com.github.tobato.fastdfs.domain.fdfs.MetaData;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;

@Slf4j
public class FastdfsUtils {

    @Autowired
    private FastFileStorageClient fastFileStorageClient;

    public StorePath upload(MultipartFile file) {
        try {
            String name = file.getOriginalFilename();
            log.info("文件名字为:" + name);
            // 获取文件信息
            HashSet<MetaData> fileSet = new HashSet<>();
            // 给元数据赋值
            fileSet.add(new MetaData("author", "fastDfs"));
            fileSet.add(new MetaData("description", file.getOriginalFilename()));
            StorePath storePath = fastFileStorageClient.uploadFile(file.getInputStream(),
                    file.getSize(),
                    FilenameUtils.getExtension(file.getOriginalFilename()),
                    null);
            log.info("结果为:{}", storePath);
            log.info("上传成功");
            return storePath;
        }catch (Exception e) {
            e.printStackTrace();
            log.error("上传失败");
            return null;
        }
    }

    private final static String[] strHex = { "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

    public static String getMD5One(String path) {
        StringBuffer sb = new StringBuffer();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] b = md.digest(FileUtils.readFileToByteArray(new File(path)));
            for (int i = 0; i < b.length; i++) {
                int d = b[i];
                if (d < 0) {
                    d += 256;
                }
                int d1 = d / 16;
                int d2 = d % 16;
                sb.append(strHex[d1] + strHex[d2]);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
