package com.vector.im.handler;

import com.vector.im.config.Config;
import com.vector.im.constant.ProtocolConstant;
import com.vector.im.entity.Packet;
import com.vector.im.im.IMChannelGroup;
import com.vector.im.im.ThreadServerSocket;
import com.vector.im.manager.IMLoginManager;
import com.vector.im.manager.IMMessageManager;
import com.vector.im.manager.IMTestManager;
import com.vector.im.manager.IMUserManager;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

/**
 * author: vector.huang
 * date：2016/4/18 19:25
 */
public class PacketChannelHandler extends ChannelInboundHandlerAdapter {

    private int id;
    private ThreadServerSocket.OnChannelActiveListener listener;

    public PacketChannelHandler(ThreadServerSocket.OnChannelActiveListener listener) {
        this.listener = listener;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        id = IMChannelGroup.instance().put(ctx.channel());
        if(listener != null){
            listener.onChannelActive(ctx);
        }
        System.out.println("连接-在线用户为: "+IMChannelGroup.instance().size());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        IMChannelGroup.instance().remove(id);
        super.channelInactive(ctx);
        System.out.println("断开-在线用户为: "+IMChannelGroup.instance().size());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Packet packet = (Packet) msg;

        //测试服务
        if(packet.getServiceId() == ProtocolConstant.SID_TEST){
            switch (packet.getCommandId()){
                case ProtocolConstant.CID_TEST_TEST_REQ:
                    IMTestManager.testReq(ctx.channel(),packet.getBody());
                    break;
            }
            return;
        }

        //登录服务
        if(packet.getServiceId() == ProtocolConstant.SID_LOGIN){
            if(packet.getCommandId() == ProtocolConstant.CID_LOGIN_IN){
                ctx.channel().close();
            }
        }

        //消息服务
        if(packet.getServiceId() == ProtocolConstant.SID_MSG){
            switch (packet.getCommandId()){
                case ProtocolConstant.CID_MSG_SEND_SINGLE_REQ:
                    IMMessageManager.sendSingleMsgReq(id,packet.getBody());
                    break;
            }
            return;
        }

    }
}
