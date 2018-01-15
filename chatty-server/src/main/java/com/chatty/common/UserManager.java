package com.chatty.common;

import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by jomalone_jia on 2018/1/11.
 */
public class UserManager {
    private static final Logger logger = LoggerFactory.getLogger(UserManager.class);

    private static ReadWriteLock lock = new ReentrantReadWriteLock();

    private static ConcurrentHashMap<Channel, User> userMap = new ConcurrentHashMap<>();
    private static AtomicInteger userCounts = new AtomicInteger();

    public static void addUser(Channel channel){
        User user = new User("", System.currentTimeMillis());
        userMap.put(channel, user);
    }
}
