package com.example.test1.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.test1.pojo.User;
import org.apache.ibatis.annotations.Mapper;


/**
 * @author OuLa-test
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
