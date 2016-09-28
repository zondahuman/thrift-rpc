package com.abin.lee.thrift.rpc.service.common.test;

import com.abin.lee.thrift.rpc.client.UserService;
import com.abin.lee.thrift.rpc.model.User;
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
public class UserServiceClientTest {

    public static void main(String[] args1) throws TException {
        TTransport transport = new TSocket("localhost", 1234);
        TProtocol protocol = new TBinaryProtocol(transport);
        UserService.Client client = new UserService.Client(protocol);
        transport.open();

        User user = client.getById(1L);
        System.out.println("user="+ user.toString());

        transport.close();

    }



}
