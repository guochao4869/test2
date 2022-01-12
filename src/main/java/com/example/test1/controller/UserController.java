package com.example.test1.controller;

import com.example.test1.pojo.Result;
import com.example.test1.pojo.User;
import com.example.test1.service.UserService;
import com.example.test1.utils.JwtUtil;
import com.github.tobato.fastdfs.domain.fdfs.MetaData;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RequestMapping("/test")
@RestController
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;


    @Autowired
    private FastFileStorageClient fastFileStorageClient;

    /**
     * showdoc
     * @catalog 测试文档/用户相关
     * @title 用户登录
     * @description 用户登录的接口
     * @method post
     * @url test/login
     * @param username 必选 string 用户名
     * @param password 必选 string 密码
     * @param role 可选 string 用户昵称
     * @return {"error_code":0,"data":{"uid":"1","username":"12154545","name":"吴系挂","groupid":2,"reg_time":"1436864169","last_login_time":"0"}}
     * @return_param groupid int 用户组id
     * @return_param name string 用户昵称
     * @remark 这里是备注信息
     * @number 99
     */
    @RequestMapping("/login")
    public Result login(@RequestBody(required = false) User user) {
        return userService.login(user);
    }

    @GetMapping("/check")
    public Result check(HttpServletRequest httpServletRequest) throws Exception{
        String authorization = httpServletRequest.getHeader("Authorization");
        if (StringUtils.isEmpty(authorization)) {
            return new Result(false, "您未登入");
        }
        Claims claims = null;
        try {
            claims = JwtUtil.parseJWT(authorization);
            if (ObjectUtils.isEmpty(claims)) {
                return new Result(false, "您的登入信息已经过期");
            }
        }catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "校验失败");
        }
        String sub = (String) claims.get("sub");
        //添加用户认证信息
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(
                sub,
                sub);
        //进行验证，这里可以捕获异常，然后返回对应信息
        subject.login(usernamePasswordToken);
        return new Result(true, "校验成功", claims);
    }

    @GetMapping("/notLogin")
    public Result notLogin() {
        return new Result(false, "您未登入");
    }

    @GetMapping("/getUser")
    public Result getUser() {
        Result result = new Result();
        result.setMeg("22");
        return result;
    }


    @GetMapping("/getOneUser")
    public Result getOneUser(@RequestParam String username) {
        return userService.getOneUser(username);
    }

    @PostMapping("/liaotian")
    public Result liaotian(@RequestBody Map<String, Object> map) {
        String meg = (String) map.get("meg");
        log.info("开始连接服务器");
        try {
            new com.example.test1.demo.client.ChatClient().startClient("service", meg);
        }catch (Exception e) {
            e.printStackTrace();
            log.error("连接服务器出现异常");
            return new Result(false, "201");
        }
        return new Result(true, "200");
    }

    @Async
    @PostMapping("/service")
    public Result service(@RequestBody Map<String, Object> map) {
        log.info("开始启动服务器");
        try {
            new com.example.test1.demo.server.ChatServer().startServer();
        }catch (Exception e) {
            e.printStackTrace();
            log.error("启动服务器出现异常");
            return new Result(false, "201");
        }
        return new Result(true, "200");
    }


    @PostMapping("/addUser")
    public Result addUser(@RequestBody(required = false) User user){
        return this.userService.addUser(user);
    }

}
