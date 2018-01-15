package com.chatty;

import com.chatty.acceptor.ChatServerStart;

/**
 * Created by jomalone_jia on 2018/1/15.
 */
public class ServerStart {
    public static void main(String[] args) throws InterruptedException {
        new ChatServerStart(8080).start();
    }
}
