package com.abin.lee.thrift.rpc.service.spring.test;

import com.abin.lee.thrift.rpc.client.OrderService;
import com.abin.lee.thrift.rpc.client.UserService;
import com.abin.lee.thrift.rpc.common.rpc.SpringClient;
import com.abin.lee.thrift.rpc.common.util.JsonUtil;
import com.abin.lee.thrift.rpc.model.Order;
import com.abin.lee.thrift.rpc.model.User;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;


public class SpringClientTest {
	private int port;
	private Map<String, String> serviceMap;
	private Map<String, Object> clientMap;
	public void setPort(int port) {
		this.port = port;
	}

	public void setServiceMap(Map<String, String> serviceMap) {
		this.serviceMap = serviceMap;
	}



	public Object getClient(String name){
		return clientMap.get(name);
	}
	
	public void init(){
		clientMap = new HashMap<String, Object>();
		try {
			TTransport transport = new TSocket("localhost", port);
			TProtocol protocol = new TBinaryProtocol(transport);
			for(Map.Entry<String, String> entry : serviceMap.entrySet()){
				String obj = entry.getValue();
				System.out.println(entry.getKey() + " " + entry.getValue());
				TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol,
						entry.getKey());
				ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
				Class<?> objectClass = classLoader.loadClass(obj + "$Client");

				Constructor<?> stor = objectClass.getDeclaredConstructor(TProtocol.class);
				Object client = stor.newInstance(mp);
				clientMap.put(entry.getKey(), client);
			}

			transport.open();

		} catch (Exception x) {
			x.printStackTrace();
		}
	}
	public void close(){
		
	}
	
	public static void main(String[] args){
		try {
			ApplicationContext context = new ClassPathXmlApplicationContext("classpath*:spring/spring-thrift-client.xml");
			SpringClient springClient = (SpringClient) context.getBean("thriftClient");

            UserService.Client userClient = (UserService.Client)springClient.getClient("UserService");
            User user = userClient.getById(111L);
            if (user != null) {
                System.out.println("登录成功： user= " + JsonUtil.toJson(user));
            } else {
                System.out.println("登录失败");
            }

            OrderService.Client orderService = (OrderService.Client)springClient.getClient("OrderService");
            Order order = orderService.getOrder(111L);
            if (order != null) {
                System.out.println("创建订单成功： order= " + JsonUtil.toJson(order));
            } else {
                System.out.println("创建订单失败");
            }

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
