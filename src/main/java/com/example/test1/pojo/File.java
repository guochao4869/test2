package com.example.test1.pojo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("file")
public class File implements Serializable {

    @ExcelProperty("ID")
    @ColumnWidth(10)
    @TableId(type = IdType.AUTO)
    private Integer id;

    @ColumnWidth(20)
    @ExcelProperty("path")
    private String path;

    @ColumnWidth(20)
    @ExcelProperty("filename")
    private String filename;

    @ColumnWidth(20)
    @ExcelProperty("name")
    private String name;

    @ColumnWidth(20)
    @ExcelProperty("md5")
    private String md5;
}
