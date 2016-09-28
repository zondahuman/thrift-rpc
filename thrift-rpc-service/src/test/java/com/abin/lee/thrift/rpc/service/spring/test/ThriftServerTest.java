package com.abin.lee.thrift.rpc.service.spring.test;

import com.abin.lee.thrift.rpc.service.OrderService;
import com.abin.lee.thrift.rpc.service.UserService;
import com.abin.lee.thrift.rpc.service.impl.OrderServiceImpl;
import com.abin.lee.thrift.rpc.service.impl.UserServiceImpl;
import org.apache.thrift.TMultiplexedProcessor;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;

/**
 * Created by abin
 * Be Created in 2016/9/28.
 */
public class ThriftServerTest {
    private void start() {
        try {
            TServerSocket serverTransport = new TServerSocket(7911);
            // 用户登录
            OrderService.Processor loginProcessor = new OrderService.Processor(
                    new OrderServiceImpl());
            // 用户注册
            UserService.Processor registerProcessor = new UserService.Processor(
                    new UserServiceImpl());
            TMultiplexedProcessor processor = new TMultiplexedProcessor();
            processor.registerProcessor("OrderService", loginProcessor);
            processor.registerProcessor("UserService", registerProcessor);
            TServer server = new TThreadPoolServer(new TThreadPoolServer.Args(
                    serverTransport).processor(processor));
            System.out.println("Starting server on port 7911 ...");
            server.serve();
        } catch (TTransportException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {
        ThriftServerTest srv = new ThriftServerTest();
        srv.start();
    }
}
