package com.example.test1.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("file")
public class File implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String path;
    private String filename;
    private String name;
    private String md5;
}
