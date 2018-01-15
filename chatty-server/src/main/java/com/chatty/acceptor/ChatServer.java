package com.chatty.acceptor;

/**
 * Created by jomalone_jia on 2018/1/10.
 */
public interface ChatServer {
    void start() throws InterruptedException;
    void shutdown();
}
