package com.chatty.handler;

import com.alibaba.fastjson.JSON;
import com.chatty.common.Message;
import com.chatty.common.UserManager;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.websocketx.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by jomalone_jia on 2018/1/11.
 */
public class AuthHandler extends SimpleChannelInboundHandler{

    private static final Logger logger = LoggerFactory.getLogger(AuthHandler.class);

    private WebSocketServerHandshaker wsHandshaker;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object o) throws Exception {
        if(o instanceof FullHttpRequest){
            handleHttpRequest(ctx,(FullHttpRequest) o);
        }else if(o instanceof WebSocketFrame){
            handleWebSocketRequest(ctx,(WebSocketFrame) o);
        }
    }

    private void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest request) {
        if (!request.decoderResult().isSuccess() || !"websocket".equals(request.headers().get("Upgrade"))) {
            logger.warn("protobuf don't support websocket");
            ctx.channel().close();
            return;
        }
        WebSocketServerHandshakerFactory factory =
                new WebSocketServerHandshakerFactory("ws://localhost:8080/websocket", null, false);
        wsHandshaker = factory.newHandshaker(request);
        if(null == wsHandshaker){
            factory.sendUnsupportedVersionResponse(ctx.channel());
        }else{
            UserManager.addUser(ctx.channel());
            wsHandshaker.handshake(ctx.channel(), request);
        }
    }

    private void handleWebSocketRequest(ChannelHandlerContext ctx,WebSocketFrame frame){
        if(frame instanceof CloseWebSocketFrame){
            wsHandshaker.close(ctx.channel(), (CloseWebSocketFrame) frame.retain());
            return;
        }else if (frame instanceof PingWebSocketFrame) {
            logger.info("ping message:{}", frame.content().retain());
            ctx.writeAndFlush(new PingWebSocketFrame(frame.content().retain()));
            return;
        }else if (frame instanceof PongWebSocketFrame) {
            logger.info("pong message:{}", frame.content().retain());
            ctx.writeAndFlush(new PongWebSocketFrame(frame.content().retain()));
            return;
        }else if (!(frame instanceof TextWebSocketFrame)) {
            throw new UnsupportedOperationException(frame.getClass().getName() + " frame type not supported");
        }else{
            String text = ((TextWebSocketFrame) frame).text();
            Message message = JSON.parseObject(text, Message.class);
            switch (message.getType()){
                case "MESSAGE":
                    break;
                case "LOGIN":
                    logger.info("type is LOGIN");
                    return;
                case "LOGOUT":
                    logger.info("type is LOGOUT");
                    return;
                default:
                    logger.info("type is UNKNOWN");
                    return;
            }
            ctx.fireChannelRead(frame.retain());
        }
    }
}
