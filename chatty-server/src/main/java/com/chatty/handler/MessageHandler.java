package com.chatty.handler;

import com.alibaba.fastjson.JSON;
import com.chatty.common.Message;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by jomalone_jia on 2018/1/10.
 */
public class MessageHandler extends SimpleChannelInboundHandler<TextWebSocketFrame>{

    private static final Logger logger = LoggerFactory.getLogger(MessageHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        String text = msg.text();
        Message message = JSON.parseObject(text, Message.class);
        logger.info("++++++++++++++++++++++++++++");
        logger.info(message.toString());
        ctx.writeAndFlush(new TextWebSocketFrame(message.getMessage()));
    }
}
