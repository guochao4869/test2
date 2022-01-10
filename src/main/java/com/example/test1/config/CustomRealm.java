package com.example.test1.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.test1.pojo.Result;
import com.example.test1.pojo.User;
import com.example.test1.service.UserService;
import com.example.test1.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;

import java.util.HashSet;
import java.util.Set;

@Slf4j
public class CustomRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        // 获取用户名  Subject代表正在操作的用户
        String username = SecurityUtils.getSubject().toString();
        Result oneUser = userService.getOneUser(username);
        if (!oneUser.getResult()) {
            return null;
        }
        User user = JSONObject.parseObject(JSONObject.toJSONString(oneUser.getData()), User.class);
        Set<String> set = new HashSet<>();
        set.add(user.getRole());
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.setStringPermissions(set);
        return simpleAuthorizationInfo;
    }

    /**
     * 获取认证的信息
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        log.info("正在进行身份认证");
        // 这里使用jwt认证
        String username = (String) authenticationToken.getPrincipal();
        Result oneUser = userService.getOneUser(username);
        if (!oneUser.getResult()) {
            return null;
        }
        User user = (User)oneUser.getData();
        return new SimpleAuthenticationInfo(user.getUsername(), user.getUsername(), this.getName());
    }
}
