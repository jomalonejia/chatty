package com.chatty.common;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by jomalone_jia on 2018/1/10.
 */
@ChannelHandler.Sharable
public class IdleTrigger extends ChannelInboundHandlerAdapter{

    private static final Logger logger = LoggerFactory.getLogger(IdleTrigger.class);

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if(evt instanceof IdleStateEvent){
            IdleStateEvent event = (IdleStateEvent) evt;
            if(event.state() == IdleState.READER_IDLE){
                logger.info("我很空闲哦~~~~~~~~~~~");
            }
        }
        super.userEventTriggered(ctx, evt);
    }
}
