package com.chatty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by jomalone_jia on 2018/1/4.
 */
public class Client {
    public static void main(String[] args) {
        NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY,true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline.addLast("framer", new DelimiterBasedFrameDecoder(2048, Delimiters.lineDelimiter()));
                            //pipeline.addLast("framer",new FixedLengthFrameDecoder(2));
                            pipeline.addLast("decoder", new StringDecoder());
                            pipeline.addLast("encoder", new StringEncoder());
                            pipeline.addLast(new ClientHandler());
                        }
                    });
            Channel channel = bootstrap.connect("localhost", 8080).sync().channel();
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            for (;;){
                String readLine = reader.readLine();
                if(null == readLine){
                    continue;
                }
                channel.writeAndFlush(readLine+"\r\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
            group.shutdownGracefully();
        }
    }
}
