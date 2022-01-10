package com.example.test1.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.test1.mapper.EsUserMapper;
import com.example.test1.mapper.UserMapper;
import com.example.test1.pojo.Result;
import com.example.test1.pojo.User;
import com.example.test1.service.UserService;
import com.example.test1.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;


/**
 * @author OuLa-test
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private EsUserMapper esUserMapper;

    @Override
    public Result login(User user) {

        User on = this.getOne(new QueryWrapper<User>().eq("username", user.getUsername())
                .eq("password", user.getPassword()));
        if (ObjectUtils.isEmpty(on)) {
            return new Result(false, "无");
        }
        // 生成jwt令牌,默认1个小时
        try {
            String jwt = JwtUtil.createJWT(on.getId(), on.getUsername(), null);
            return new Result(true, "成功", jwt);
        }catch (Exception e) {
            return new Result(false, "失败");
        }
    }


    @Override
    public Result getOneUser(String username) {
      /*  // 优先做es查询
        RestTemplate restTemplate = new RestTemplate();
        // 构造查询数据
        Map<String, Object> map1 = new HashMap<>(16);
        Map<String, Object> map2 = new HashMap<>(16);
        Map<String, Object> map3 = new HashMap<>(16);
        map1.put("username", username);
        map2.put("match", map1);
        map3.put("query", map2);
        ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity("http://127.0.0.1:9200/test/type1/_search?pretty" , map3, String.class);
        log.info("执行结果{}" + stringResponseEntity.getBody());
        JSONObject jsonObject = JSONObject.parseObject(stringResponseEntity.getBody());
        JSONObject jsonObject1 = (JSONObject) jsonObject.get("hits");
        if (!ObjectUtils.isEmpty(jsonObject1)) {
            return new Result(true, "成功", jsonObject1);
        }
*/
        User in = this.baseMapper.selectOne(new QueryWrapper<User>().eq("username", username).last("limit 1"));
        if (ObjectUtils.isEmpty(in)) {
            return new Result(false, "无");
        }
        return new Result(true, "成功", in);
    }

    @Override
    public Result addUser(User user) {
        user.setRole("user");
        boolean save = this.save(user);
        if (!save) {
            return new Result(false, "201", "失败");
        }
        HashMap<String, Object> map = new HashMap<>(1);
        // 同步es
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity("http://127.0.0.1:9200/test/type1/" + user.getId(), user, String.class);
        log.info("执行结果:{}", stringResponseEntity.getBody());
        return new Result(true, "200", "成功");
    }

}
