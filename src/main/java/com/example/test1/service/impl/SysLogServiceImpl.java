package com.example.test1.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.test1.mapper.SysLogMapper;
import com.example.test1.pojo.SysLog;
import com.example.test1.service.SysLogService;
import org.springframework.stereotype.Service;

/**
 * @author OuLa-test
 */
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements SysLogService {
}
