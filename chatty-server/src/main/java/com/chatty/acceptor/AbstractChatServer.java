package com.chatty.acceptor;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.util.concurrent.DefaultThreadFactory;
import io.netty.util.internal.PlatformDependent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.SocketAddress;
import java.util.concurrent.ThreadFactory;

/**
 * Created by jomalone_jia on 2018/1/10.
 */
public abstract class AbstractChatServer implements ChatServer {

    private static final Logger logger = LoggerFactory.getLogger(AbstractChatServer.class);

    private static final int AVAILABLEPROCESSORS = Runtime.getRuntime().availableProcessors();

    protected final SocketAddress address;

    private ServerBootstrap bootstrap;
    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;
    private int workThreadCount;

    protected volatile ByteBufAllocator allocator;

    public AbstractChatServer(SocketAddress address) {
        this(address, AVAILABLEPROCESSORS << 1);
    }

    public AbstractChatServer(SocketAddress address, int workThreadCount) {
        this.address = address;
        this.workThreadCount = workThreadCount;
    }

    protected void init(){
        ThreadFactory bossFactory = new DefaultThreadFactory("bossAcceptor");
        ThreadFactory workerFactory = new DefaultThreadFactory("workerAcceptor");
        bossGroup = initEventLoopGroup(1, bossFactory);
        workerGroup = initEventLoopGroup(workThreadCount, workerFactory);
        allocator = new PooledByteBufAllocator(PlatformDependent.directBufferPreferred());
        bootstrap = new ServerBootstrap().group(bossGroup, workerGroup);
        bootstrap.childOption(ChannelOption.ALLOCATOR, allocator);
    }

    abstract protected EventLoopGroup initEventLoopGroup(int workThreadCount,ThreadFactory threadFactory);

    abstract protected ChannelFuture bind(SocketAddress address);

    @Override
    public void start() throws InterruptedException {
        ChannelFuture channelFuture = this.bind(address).sync();
        logger.info("server run on " + address);
        channelFuture.channel().closeFuture().sync();
    }

    @Override
    public void shutdown() {
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }

    protected ServerBootstrap getBootstrap(){
        return bootstrap;
    }
}
