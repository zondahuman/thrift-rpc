package com.abin.lee.thrift.rpc.service.test;

import com.abin.lee.thrift.rpc.service.OrderService;
import com.abin.lee.thrift.rpc.service.impl.OrderServiceImpl;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;

/**
 * Created with IntelliJ IDEA.
 * User: abin
 * Date: 16-8-29
 * Time: 下午11:30
 * To change this template use File | Settings | File Templates.
 */
public class OrderServiceTest {

    public static void main(String[] args1) throws TTransportException {
        System.out.println("-----------server  starting--------");
        TServerSocket tServerSocket = new TServerSocket(1235);
        OrderService.Processor processor = new OrderService.Processor(new OrderServiceImpl());

        TBinaryProtocol.Factory factory  = new TBinaryProtocol.Factory(true, true);
        TThreadPoolServer.Args args = new TThreadPoolServer.Args(tServerSocket);
        args.processor(processor);
        args.protocolFactory(factory);
        TServer server = new TThreadPoolServer(args);
        server.serve();
        System.out.println("-----------server  started--------");


    }



}
