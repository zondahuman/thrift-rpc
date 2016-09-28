package com.abin.lee.thrift.rpc.service.spring.test;

import com.abin.lee.thrift.rpc.client.OrderService;
import com.abin.lee.thrift.rpc.client.UserService;
import com.abin.lee.thrift.rpc.common.util.JsonUtil;
import com.abin.lee.thrift.rpc.model.Order;
import com.abin.lee.thrift.rpc.model.User;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

/**
 * Created by abin
 * Be Created in 2016/9/28.
 */
public class ThriftClientTest {

    public static void main(String[] args) {
        try {
            TTransport transport = new TSocket("localhost", 7911);
            TProtocol protocol = new TBinaryProtocol(transport);
            TMultiplexedProtocol mp1 = new TMultiplexedProtocol(protocol, "UserService");
            UserService.Client userClient = new UserService.Client(mp1);
            TMultiplexedProtocol mp2 = new TMultiplexedProtocol(protocol, "OrderService");
            OrderService.Client orderClient = new OrderService.Client(mp2);
            transport.open();

            User user = userClient.getById(111L);
            if (user != null) {
                System.out.println("登录成功： user= " + JsonUtil.toJson(user));
            } else {
                System.out.println("登录失败");
            }
            Order order = orderClient.getOrder(222L);
            if (order != null) {
                System.out.println("创建订单成功： order= " + JsonUtil.toJson(order));
            } else {
                System.out.println("创建订单失败");
            }
            transport.close();
        } catch (TException x) {
            x.printStackTrace();
        }
    }

}
