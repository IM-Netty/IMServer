package com.vector.im.im;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * author: vector.huang
 * date：2016/4/18 1:31
 */
public class IMServer{

    private static IMServer instance;
    private static Lock lock = new ReentrantLock();

    private IMServer(){}
    public static IMServer instance(){
        if(instance == null){
            lock.lock();
            if(instance == null){
                instance = new IMServer();
            }
            lock.unlock();
        }
        return instance;
    }

    public void run() {
        try {
            start(8080);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private  ChannelFuture channelFuture;

    public void stop(){
        if(channelFuture == null||channelFuture.channel() == null){
            channelFuture = null;
            System.out.println("服务器未开启...");
            return;
        }

        if(channelFuture.channel().isOpen()){
            channelFuture.channel().close();
            System.out.println("服务器关闭中...");
        }

    }

    public void start(int port) throws InterruptedException {
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();

        try {
            ServerBootstrap boot = new ServerBootstrap();
            boot.group(boss,worker);

            boot.channel(NioServerSocketChannel.class);
            boot.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new StringDecoder());
                    ch.pipeline().addLast(new StringEncoder());

                    ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                        @Override
                        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                            System.out.println(msg);
                            ctx.writeAndFlush(msg+" -- Server");
                        }
                    });
                }
            });

            boot.option(ChannelOption.SO_BACKLOG,128);
            boot.childOption(ChannelOption.SO_KEEPALIVE,true);
            channelFuture = boot.bind(port).sync();
            System.out.println("服务器开启成功...");
            channelFuture.channel().closeFuture().sync();
        }finally {
            boss.shutdownGracefully().sync();
            worker.shutdownGracefully().sync();
            channelFuture = null;
            System.out.println("服务器关闭成功...");
        }
    }
}
