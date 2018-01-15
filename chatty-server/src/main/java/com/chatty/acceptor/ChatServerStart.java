package com.chatty.acceptor;

import com.chatty.common.IdleTrigger;
import com.chatty.handler.AuthHandler;
import com.chatty.handler.MessageHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * Created by jomalone_jia on 2018/1/10.
 */
public class ChatServerStart extends AbstractChatServer{

    public ChatServerStart(int port){
        super(new InetSocketAddress(port));
        this.init();
    }

    @Override
    protected void init() {
        super.init();
        getBootstrap()
                .option(ChannelOption.SO_BACKLOG,2048)
                .childOption(ChannelOption.SO_KEEPALIVE,true)
                .childOption(ChannelOption.SO_KEEPALIVE,true);
    }



    @Override
    protected EventLoopGroup initEventLoopGroup(int workThreadCount, ThreadFactory threadFactory) {
        return new NioEventLoopGroup(workThreadCount, threadFactory);
    }

    @Override
    protected ChannelFuture bind(SocketAddress address) {
        ServerBootstrap bootstrap = getBootstrap();
        bootstrap
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel
                                .pipeline()
                                .addLast(
                                        new IdleStateHandler(15, 0, 0, TimeUnit.SECONDS),
                                        new IdleTrigger(),
                                        new HttpServerCodec(),
                                        new HttpObjectAggregator(8192),
                                        new ChunkedWriteHandler(),
                                        new AuthHandler(),
                                        new MessageHandler()
                                );
                    }
                })
                ;
        return bootstrap.bind(address);
    }
}
