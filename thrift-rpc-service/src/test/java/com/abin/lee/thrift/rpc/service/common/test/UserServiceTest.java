package com.abin.lee.thrift.rpc.service.common.test;

import com.abin.lee.thrift.rpc.service.UserService;
import com.abin.lee.thrift.rpc.service.impl.UserServiceImpl;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: abin
 * Date: 16-8-29
 * Time: 下午11:30
 * To change this template use File | Settings | File Templates.
 */
public class UserServiceTest {

    public static void main(String[] args1) throws TTransportException {
        System.out.println("-----------server  starting--------");
        TServerSocket tServerSocket = new TServerSocket(1234);
        UserService.Processor processor = new UserService.Processor(new UserServiceImpl());

        TBinaryProtocol.Factory factory  = new TBinaryProtocol.Factory(true, true);
        TThreadPoolServer.Args args = new TThreadPoolServer.Args(tServerSocket);
        args.processor(processor);
        args.protocolFactory(factory);
        TServer server = new TThreadPoolServer(args);
        server.serve();
        System.out.println("-----------server  started--------");


    }



}
