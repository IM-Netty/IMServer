package com.vector.im.app;

import com.vector.im.im.IMServer;

/**
 * author: vector.huang
 * date：2016/4/18 1:18
 */
public class App {

    private static App instance =  new App();

    private App(){}

    public static App instance(){
        return instance;
    }

    public void create(){
        IMServer.instance().run(); //开始服务
    }

    public void destroy(){
        IMServer.instance().stop();
    }

}
