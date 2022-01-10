package com.example.test1.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Servlet;
import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * druid配置类
 * @author gc
 * @date 2021年12月16日 14:47:32
 */
@Configuration
public class DruidConfig {

    /**
     * 注入德鲁伊配置
     * @return
     */
    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    public DataSource druid(){
        DruidDataSource dataSource = new DruidDataSource();
        return dataSource;
    }

    @Bean
    public ServletRegistrationBean druidServlet(){
        ServletRegistrationBean<Servlet> servletServletRegistrationBean = new ServletRegistrationBean<>();
        servletServletRegistrationBean.setServlet(new StatViewServlet());
        servletServletRegistrationBean.addUrlMappings("/druid/*");
        // 配置监控的用户名和密码
        Map<String, String> map = new HashMap<>(16);
        // 用户名
        map.put("loginUsername", "root");
        // 密码
        map.put("loginPassword", "root");
        // 白名单value是ip地址,如果为空就是全部权限
        map.put("allow", "");
        map.put("resetEnable", "false");
        // 黑名单,如果白名单和黑名单都有黑名单优先级更高
        // map.put("deny", "");
        servletServletRegistrationBean.setInitParameters(map);
        return servletServletRegistrationBean;
    }
    /*@Bean
    public FilterRegistrationBean WebStatFilter(){
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new WebStatFilter());
        Map<String,String > map = new HashMap<>();
        map.put("exclusions","*.js,*.css,/druid/*");
        bean.setInitParameters(map);
        //设置拦截请求，这里设置拦截所有请求
        bean.setUrlPatterns(Arrays.asList("/*"));
        return bean;
    }*/
}
