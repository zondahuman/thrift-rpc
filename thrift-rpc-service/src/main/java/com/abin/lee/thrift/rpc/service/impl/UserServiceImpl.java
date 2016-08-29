package com.abin.lee.thrift.rpc.service.impl;

import com.abin.lee.thrift.rpc.model.User;
import com.abin.lee.thrift.rpc.service.UserService;
import org.apache.thrift.TException;

/**
 * Created with IntelliJ IDEA.
 * User: abin
 * Date: 16-8-29
 * Time: 下午11:26
 * To change this template use File | Settings | File Templates.
 */
public class UserServiceImpl implements UserService.Iface {

    @Override
    public User getById(long id) throws TException {
        User user = new User();
        user.setId(id);
        user.setName("abin");
        user.setTimestamp(1111111111111111111L);
        user.setVip(true);
        return user;
    }




}
