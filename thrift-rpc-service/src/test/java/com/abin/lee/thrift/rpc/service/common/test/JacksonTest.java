package com.abin.lee.thrift.rpc.service.common.test;

import com.abin.lee.thrift.rpc.common.util.JsonUtil;
import com.abin.lee.thrift.rpc.service.model.User;
import com.fasterxml.jackson.core.type.TypeReference;

/**
 * Created with IntelliJ IDEA.
 * User: abin
 * Date: 17-2-24
 * Time: 下午9:42
 * To change this template use File | Settings | File Templates.
 */
public class JacksonTest {

    public static void main(String[] args) {
//        String json = "{\"name\":\"lee\",\"age\":7,\"address\":\"beijing\",\"email\":\"abin@gmail.com\"}";
        String json = "{\"name\":\"lee\",\"age\":7,\"address\":\"beijing\",\"email\":\"abin@gmail.com\",\"relation\":\"parent\"}";
        User user = JsonUtil.decodeJson(json, new TypeReference<User>() {
        });
        System.out.println(JsonUtil.toJson(user));


    }



}
