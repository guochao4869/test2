package com.example.test1.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 日志
 * @author gc
 * @date 2022年4月12日 16:09:56
 */
@Data
@TableName(value = "sys_log")
public class SysLog {
    @TableId
    private String logId;
    private String description;
    private String requestUri;
    private String params;
    private String httpMethod;
    private String requestIp;
    private String ua;
    private String result;
    private String exDesc;
    private String exDetail;
    private Integer type;
    private Integer code;
    private Date startTime;
    private Date finishTime;
    private Date consumingTime;
    private Integer isDel;
    private String createUser;
    private Date createTime;
    private String updateUser;
    private Date updateTime;
}
