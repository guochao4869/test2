package com.example.test1.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * shiro配置类
 * @author gc
 */

@Configuration
@Slf4j
public class ShiroConfig {

    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager){
        log.info("shiro工厂开始注入");
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        shiroFilterFactoryBean.setLoginUrl("/test/check");
        // 设置无权限跳转的url
        shiroFilterFactoryBean.setUnauthorizedUrl("/test/notLogin");
        // 设置拦截器 anon代表公开  authc代表需要权限校验
        Map<String, String> filterMap = new LinkedHashMap<>(16);
        filterMap.put("/test/login", "anon");
        filterMap.put("/test/getOneUser", "anon");
        filterMap.put("/test/fileTest", "anon");
        filterMap.put("/test/getUser", "anon");
        filterMap.put("/druid/**", "anon");
        // 其余的都需要校验权限,这个一定要放到最后，不然不生效
        filterMap.put("/**", "authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
        log.info("shiro工厂注入成功");
        return shiroFilterFactoryBean;
    }

    /**
     * 注入 securityManager
     */
    @Bean({"securityManager"})
    public DefaultWebSecurityManager securityManager() {
        log.info("开始注入securityManager");
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        // 设置自定义身份
        defaultWebSecurityManager.setRealm(customRealm());
        log.info("注入securityManager成功");
        return defaultWebSecurityManager;
    }

    /**
     * 自定义身份认证 realm;
     * <p>
     * 必须写这个类，并加上 @Bean 注解，目的是注入 CustomRealm，
     * 否则会影响 CustomRealm类 中其他类的依赖注入
     */
    @Bean
    public CustomRealm customRealm() {
        return new CustomRealm();
    }
}
