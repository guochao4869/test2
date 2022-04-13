package com.example.test1.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.test1.exception.GlobalExceptionHandler;
import com.example.test1.exception.MyException;
import com.example.test1.mapper.FileMapper;
import com.example.test1.pojo.File;
import com.example.test1.pojo.Result;
import com.example.test1.service.FileService;
import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;

/**
 * @author gc
 * @date 2021年12月17日 16:18:21
 */
@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, File> implements FileService {

    @Autowired
    private FileMapper fileMapper;

    @Autowired
    PlatformTransactionManager transactionManager;


    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result find() throws MyException {
        List<File> files = fileMapper.find();
        return new Result(true, "1", files);
    }
}
