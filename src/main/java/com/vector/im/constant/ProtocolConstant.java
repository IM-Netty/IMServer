package com.vector.im.constant;

/**
 * author: vector.huang
 * date：2016/4/18 19:31
 */
public interface ProtocolConstant {

    short VERSION = 1;
    short RESERVED = 0;

    short SID_TEST = -1;
    short CID_TEST_TEST_REQ = 1;
    short CID_TEST_TEST_RSP = 2;

    short SID_USER = 1;
    short CID_USER_INFO = 1;


    short SID_MSG = 2;
    short CID_MSG_SEND_SINGLE_REQ = 1;
    short CID_MSG_RECEIVE_SINGLE_OUT = 2;


}
