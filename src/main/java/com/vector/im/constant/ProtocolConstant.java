package com.vector.im.constant;

/**
 * author: vector.huang
 * date：2016/4/18 19:31
 */
public interface ProtocolConstant {

    short VERSION = 1; //版本号
    short RESERVED = 0;//保留字段

    short SID_TEST = -1;// 测试服务
    short CID_TEST_TEST_REQ = 1;
    short CID_TEST_TEST_RSP = 2;

    short SID_LOGIN = 0;//登录
    short CID_LOGIN_OUT = 1; //发送登录IP和端口
    short CID_LOGIN_IN = 2; //接受成功

    short SID_USER = 1;
    short CID_USER_INFO = 1;


    short SID_MSG = 2;
    short CID_MSG_SEND_SINGLE_REQ = 1;
    short CID_MSG_RECEIVE_SINGLE_OUT = 2;


}
