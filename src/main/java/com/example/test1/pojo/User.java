package com.example.test1.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author OuLa-test
 */
@Data
@TableName("user")
public class User implements Serializable {
    private String id;
    private String username;
    private String password;
    private String role;
}
