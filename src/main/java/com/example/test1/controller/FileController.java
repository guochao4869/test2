package com.example.test1.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.example.test1.config.OperLog;
import com.example.test1.exception.MyException;
import com.example.test1.pojo.File;
import com.example.test1.pojo.Result;
import com.example.test1.service.FileService;
import com.example.test1.service.impl.FileServiceImpl;
import com.example.test1.utils.FastdfsUtils;
import com.github.tobato.fastdfs.domain.fdfs.MetaData;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.net.http.HttpResponse;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * @author OuLa-test
 */
@RequestMapping("/file")
@RestController
@Slf4j
public class FileController {
    @Autowired
    private FileServiceImpl fileService;

    @Autowired
    private FastFileStorageClient fastFileStorageClient;

    @PostMapping("/fileTest")
    public Result fileTest(@RequestParam("file") MultipartFile file){
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
            String path = "/" + storePath.getGroup() + "/" + storePath.getPath();
            File filePojo = new File();
            filePojo.setFilename(name);
            filePojo.setMd5("md5");
            filePojo.setPath("http://192.168.10.241:8080" + path);
            filePojo.setName(path);
            boolean save = this.fileService.save(filePojo);
            if (!save) {
                return new Result(false, 201);
            }
            Result result = new Result(true, 200, filePojo.getPath());
            return result;
        }catch (Exception e) {
            e.printStackTrace();
            log.error("上传失败");
            return new Result(false, 201);
        }
    }

    @GetMapping("/find")
    public Result find(){
        return this.fileService.find();
    }

    /**
     * showdoc
     * @catalog 测试文档/文件
     * @title 测试日志
     * @description 测试日志
     * @method post
     * @url file/logTest
     * @return {"error_code":0,"data":{"uid":"1","username":"12154545","name":"吴系挂","groupid":2,"reg_time":"1436864169","last_login_time":"0"}}
     * @return_param groupid int 用户组id
     * @return_param name string 用户昵称
     * @remark 这里是备注信息
     * @number 99
     */
    @PostMapping("/logTest")
    @OperLog(operModul = "测试日志")
    public Result logTest(@RequestBody Map<String, Object> map){
        return this.fileService.logTest();
    }

    @GetMapping("/getExcel")
    public void getExcel(HttpServletResponse response){
        try {
            setExcelRespProp(response, "测试");
            List<File> files = fileService.getBaseMapper().selectList(null);
            EasyExcel.write(response.getOutputStream())
                    .head(File.class)
                    .excelType(ExcelTypeEnum.XLSX)
                    .sheet("文件列表")
                    .doWrite(files);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 设置响应头
     * @param response
     * @param fileName
     * @throws Exception
     */
    public void setExcelRespProp(HttpServletResponse response, String fileName) throws Exception{
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String name = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + name + ".xlsx");
    }
}
