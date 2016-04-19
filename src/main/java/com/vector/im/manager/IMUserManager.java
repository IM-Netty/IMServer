package com.vector.im.manager;

import com.vector.im.constant.ProtocolConstant;
import com.vector.im.entity.Packet;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;

/**
 * author: vector.huang
 * date：2016/4/18 22:37
 */
public class IMUserManager {

    public static void sendUserInfo(Channel channel, int id){
        ByteBuf byteBuf = channel.alloc().buffer();
        byteBuf.writeInt(id);
        Packet packet = new Packet(byteBuf.readableBytes() + 12, ProtocolConstant.SID_USER,
                ProtocolConstant.CID_USER_INFO,byteBuf);

        channel.writeAndFlush(packet);
    }

}
