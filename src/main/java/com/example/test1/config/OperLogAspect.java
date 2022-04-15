package com.example.test1.config;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.example.test1.pojo.Result;
import com.example.test1.pojo.SysLog;
import com.example.test1.service.SysLogService;
import com.example.test1.service.impl.SysLogServiceImpl;
import com.example.test1.utils.IpUtil;
import com.example.test1.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * 日志切面配置
 * @author gc
 * @date 2022年4月12日 16:06:59
 */

@Slf4j
@Aspect
@Component
public class OperLogAspect {

    @Autowired
    private SysLogServiceImpl sysLogService;

    /**
     * 在有注解时切入
     */
    @Pointcut("@annotation(com.example.test1.config.OperLog)")
    public void operLogPoinCut(){}

    /**
     * 扫描所有的mvc层
     */
    @Pointcut("execution(* com.example.test1.controller..*.*(..))")
    public void operExceptionLogPoinCut() {}

    /**
     * 正常返回日志插入
     * @param joinPoint 切入点
     * @param result 返回结果
     */
    @AfterReturning(value = "operLogPoinCut()", returning = "result")
    public void saveOperLog(JoinPoint joinPoint, Result result){
        // 首先获取请求体
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest httpServletRequest = (HttpServletRequest)requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
        // 插入日志
        SysLog sysLog = new SysLog();
        try {
            // 生成雪花算法id，不生成也会自动生成
            sysLog.setLogId(IdWorker.getIdStr());
            // 获取注解的内容
            // 通过返回获取到接口内容
            MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
            Method method = methodSignature.getMethod();
            // 获取到注解

            OperLog annotation = method.getAnnotation(OperLog.class);
            if (ObjectUtils.isNotEmpty(annotation)) {
                // 注解的数据
                sysLog.setDescription(annotation.operModul());
            }
            // 获取接口名字
            sysLog.setRequestUri(httpServletRequest.getServletPath());
            // 获取到json参数
            Object[] args = joinPoint.getArgs();
            //先判断是否是json参数（目前只支持Json）
            if (!httpServletRequest.getContentType().contains("multipart/form-data")) {
                // 是json
                sysLog.setParams(JSONObject.toJSONString(args));
            }else {
                sysLog.setParams("");
            }
            // 请求类型目前也只能是Post
            sysLog.setHttpMethod(httpServletRequest.getMethod());
            // 获取的请求的ip
            sysLog.setRequestIp(IpUtil.getIpAddr(httpServletRequest));
            // 返回值
            sysLog.setResult(JSONObject.toJSONString(result));
            sysLog.setType(1);
            sysLog.setCode(result.getCode());
            sysLog.setStartTime(new Date());
            // 获取用户的id
            String authorization = httpServletRequest.getHeader("Authorization");
            Claims claims = JwtUtil.parseJWT(authorization);
            sysLog.setCreateUser(claims.getId());
            sysLog.setUpdateUser(claims.getId());
            sysLog.setCreateTime(new Date());
            sysLog.setUpdateTime(new Date());
            boolean save = sysLogService.save(sysLog);
            if (!save) {
                log.error("插入日志失败");
            }
            log.info("插入日志成功");
        }catch (Exception e) {
            e.printStackTrace();
            log.error("插入出现异常");
        }
    }

    @AfterThrowing(pointcut = "operExceptionLogPoinCut()", throwing = "throwable")
    public void saveExceptionLog(JoinPoint joinPoint, Throwable throwable) {
        // 首先获取请求体
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest httpServletRequest = (HttpServletRequest)requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
        // 插入日志
        SysLog sysLog = new SysLog();
        try {
            // 生成雪花算法id，不生成也会自动生成
            sysLog.setLogId(IdWorker.getIdStr());
            // 获取注解的内容
            // 通过返回获取到接口内容
            MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
            Method method = methodSignature.getMethod();
            // 获取到注解

            OperLog annotation = method.getAnnotation(OperLog.class);
            if (ObjectUtils.isNotEmpty(annotation)) {
                // 注解的数据
                sysLog.setDescription(annotation.operModul());
            }
            // 获取接口名字
            sysLog.setRequestUri(httpServletRequest.getServletPath());
            // 获取到json参数
            Object[] args = joinPoint.getArgs();
            //先判断是否是json参数（目前只支持Json）
            if (!httpServletRequest.getContentType().contains("multipart/form-data")) {
                // 是json
                sysLog.setParams(JSONObject.toJSONString(args));
            }else {
                sysLog.setParams("");
            }
            // 请求类型目前也只能是Post
            sysLog.setHttpMethod(httpServletRequest.getMethod());
            // 获取的请求的ip
            sysLog.setRequestIp(IpUtil.getIpAddr(httpServletRequest));
            // 返回值
            sysLog.setExDesc(throwable.getClass().getName());
            sysLog.setExDetail(parExStr(throwable.getClass().getName(), throwable.getMessage(), throwable.getStackTrace()));
            sysLog.setResult(JSONObject.toJSONString(throwable.getMessage()));
            //sysLog.setResult(JSONObject.toJSONString(result));
            sysLog.setType(2);
            sysLog.setStartTime(new Date());
            // 获取用户的id
            String authorization = httpServletRequest.getHeader("Authorization");
            Claims claims = JwtUtil.parseJWT(authorization);
            sysLog.setCreateUser(claims.getId());
            sysLog.setUpdateUser(claims.getId());
            sysLog.setCreateTime(new Date());
            sysLog.setUpdateTime(new Date());
            boolean save = sysLogService.save(sysLog);
            if (!save) {
                log.error("插入日志失败");
            }
            log.info("插入日志成功");
        }catch (Exception e) {
            e.printStackTrace();
            log.error("插入出现异常");
        }
    }

    /**
     * 把异常信息转换为字符串
     * @param exceptionName 异常的名字
     * @param exceptionMessage 异常的内容
     * @param elements 堆栈信息
     * @return
     */
    public String parExStr(String exceptionName, String exceptionMessage, StackTraceElement[] elements){
        StringBuffer stringBuffer = new StringBuffer();
        for (StackTraceElement element : elements) {
            stringBuffer.append(element + "\n");
        }
        String meg = "异常名字:" + exceptionName + ",异常的内容:" + exceptionMessage + "\n\t" + stringBuffer;
        return meg;
    }
}
