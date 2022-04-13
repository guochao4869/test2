package com.example.test1.config;

import java.lang.annotation.*;

/**
 * 自定义注解
 * @author gc
 * @date 2022年4月12日 16:05:43
 */
@Target(ElementType.METHOD) //注解放置的目标位置,METHOD是可注解在方法级别上
@Retention(RetentionPolicy.RUNTIME) //注解在哪个阶段执行
@Documented
public @interface OperLog {
    String operModul() default ""; // 操作模块
}
