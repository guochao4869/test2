package com.example.test1.utils;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;

public class JSONUtils {
    public static String jsonMap(String message, String msg){
        HashMap<String, Object> map = new HashMap<>(3);
        map.put("code", msg);
        map.put("result", false);
        map.put("data", message);
        return map.toString();
    }
}
