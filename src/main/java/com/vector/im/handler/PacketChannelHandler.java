package com.vector.im.handler;

import com.vector.im.constant.ProtocolConstant;
import com.vector.im.entity.Packet;
import com.vector.im.im.IMChannelGroup;
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

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        id = IMChannelGroup.instance().put(ctx.channel());
        IMUserManager.sendUserInfo(ctx.channel(),id);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        Packet packet = (Packet) msg;

        if(packet.getServiceId() == ProtocolConstant.SID_TEST){
            switch (packet.getCommandId()){
                case ProtocolConstant.CID_TEST_TEST_REQ:
                    IMTestManager.testReq(ctx.channel(),packet.getBody());
                    break;
            }
            return;
        }

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
