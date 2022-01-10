package com.example.test1.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.test1.mapper.FileMapper;
import com.example.test1.pojo.File;
import com.example.test1.service.FileService;
import org.springframework.stereotype.Service;

/**
 * @author gc
 * @date 2021年12月17日 16:18:21
 */
@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, File> implements FileService {
}
