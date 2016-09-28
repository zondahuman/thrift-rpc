package com.abin.lee.thrift.rpc.controller;

import com.abin.lee.thrift.rpc.client.OrderService;
import com.abin.lee.thrift.rpc.client.UserService;
import com.abin.lee.thrift.rpc.common.context.SpringContextUtils;
import com.abin.lee.thrift.rpc.common.rpc.SpringClient;
import com.abin.lee.thrift.rpc.common.util.JsonUtil;
import com.abin.lee.thrift.rpc.model.Order;
import com.abin.lee.thrift.rpc.model.User;
import com.google.common.primitives.Longs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by abin
 * Be Created in 2016/9/28.
 */
@Controller
@RequestMapping("/load")
public class RpcController {
    protected final static Logger LOGGER = LoggerFactory.getLogger(RpcController.class);

    @Resource
    SpringClient thriftClient;

    @ResponseBody
    @RequestMapping(value = "/userInfo")
    public String userInfo(String param) {
        String result = "";
        try {
            SpringClient springClient = (SpringClient) SpringContextUtils.getBean("thriftClient");
            UserService.Client userClient = (UserService.Client) springClient.getClient("UserService");
            User user = userClient.getById(Longs.tryParse(param));
            if (user != null) {
                result = JsonUtil.toJson(user);
                System.out.println("登录成功： user= " + result);
            } else {
                System.out.println("登录失败");
            }

        } catch (Exception e) {
            LOGGER.error("e={} message={}", e, e.getMessage());
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/orderInfo")
    public String orderInfo(String param) {
        String result = "";
        try {
            SpringClient springClient = (SpringClient) SpringContextUtils.getBean("thriftClient");

            OrderService.Client orderService = (OrderService.Client)springClient.getClient("OrderService");
            Order order = orderService.getOrder(Longs.tryParse(param));
            if (order != null) {
                result = JsonUtil.toJson(order);
                System.out.println("创建订单成功： order= " + result);
            } else {
                System.out.println("创建订单失败");
            }

        } catch (Exception e) {
            LOGGER.error("e={} message={}", e, e.getMessage());
        }
        return result;
    }




}
