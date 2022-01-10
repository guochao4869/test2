package com.example.test1.exception;

import com.example.test1.pojo.Result;
import org.apache.log4j.spi.ErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 全局异常处理器
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    //捕获异常
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)//返回500状态码
    public Result processExcetion(HttpServletRequest request, HttpServletResponse response, Exception e){
        //异常处理
        //系统自定义异常取出errCode和errMessage
        if (e instanceof MyException){
            //控制台打印
            LOGGER.info(e.getMessage(),e);
            //解析系统自定义异常
            MyException businessException = (MyException) e;
            String errorCode = businessException.getMessage();
            return new Result(false, "201", errorCode);
        }
        //非自定义异常类型，定义为99999系统未知错误
        LOGGER.error("系统异常:",e);
        return new Result(false, "99999");
    }
}
