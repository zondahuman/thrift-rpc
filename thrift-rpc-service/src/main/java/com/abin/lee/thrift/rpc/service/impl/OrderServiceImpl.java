package com.abin.lee.thrift.rpc.service.impl;

import com.abin.lee.thrift.rpc.model.Order;
import com.abin.lee.thrift.rpc.model.User;
import com.abin.lee.thrift.rpc.service.OrderService;
import com.abin.lee.thrift.rpc.service.UserService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.thrift.TException;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: abin
 * Date: 16-8-29
 * Time: 下午11:26
 * To change this template use File | Settings | File Templates.
 */
public class OrderServiceImpl implements OrderService.Iface{

    @Override
    public Order getOrder(long orderId) throws TException {
        Order order = new Order();
        order.setId(orderId);
        order.setName("lee");
        Map<Integer, String> addressMap = Maps.newHashMap();
        addressMap.put(1, "beijing");
        addressMap.put(2, "shanghai");
        order.setAddressMap(addressMap);
        List<Integer> catagoryList = Lists.newArrayList();
        catagoryList.add(100);
        catagoryList.add(200);
        order.setCatagoryList(catagoryList);
        Set<Integer> colorSet = Sets.newHashSet();
        colorSet.add(1000);
        colorSet.add(2000);
        order.setColorSet(colorSet);

        return order;
    }



}










