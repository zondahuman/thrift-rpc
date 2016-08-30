package com.abin.lee.thrift.rpc.service.test;

import com.abin.lee.thrift.rpc.client.UserService;
import com.abin.lee.thrift.rpc.model.Order;
import com.abin.lee.thrift.rpc.model.User;
import com.abin.lee.thrift.rpc.service.OrderService;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

/**
 * Created with IntelliJ IDEA.
 * User: abin
 * Date: 16-8-29
 * Time: 下午11:30
 * To change this template use File | Settings | File Templates.
 */
public class OrderServiceClientTest {

    public static void main(String[] args1) throws TException {
        TTransport transport = new TSocket("localhost", 1235);
        TProtocol protocol = new TBinaryProtocol(transport);
        OrderService.Client client = new OrderService.Client(protocol);
        transport.open();

        Order order = client.getOrder(1L);
        System.out.println("order="+ order.toString());

        transport.close();

    }



}
