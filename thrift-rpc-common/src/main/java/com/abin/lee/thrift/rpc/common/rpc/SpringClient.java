package com.abin.lee.thrift.rpc.common.rpc;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import com.abin.lee.thrift.rpc.common.util.JsonUtil;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;



public class SpringClient {
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
			ApplicationContext context = new ClassPathXmlApplicationContext("spring-context-thrift-client.xml");
			SpringClient springClient = (SpringClient) context.getBean("thriftServer");
//            UserService.Client loginService = (UserService.Client)springClient.getClient("UserService");
//
//            User user = userClient.getById(111L);
//            if (user != null) {
//                System.out.println("登录成功： user= " + JsonUtil.toJson(user));
//            } else {
//                System.out.println("登录失败");
//            }
//
//			RegisterService.Client registerClient = (RegisterService.Client)springClient.getClient("RegisterService");
//			User user2 = registerClient.createUser("test", "123");
//			if (user2 != null) {
//				System.out.println("创建用户成功：" + user2.getId() + " "
//						+ user2.getName());
//			} else {
//				System.out.println("创建用户失败");
//			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
