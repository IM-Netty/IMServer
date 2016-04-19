package com.vector.im;

import com.vector.im.app.App;

import sun.misc.Signal;
import sun.misc.SignalHandler;

/**
 * author: vector.huang
 * date：2016/4/18 1:15
 */
public class Boot extends Thread implements SignalHandler{

    private void boot(){
        Runtime.getRuntime().addShutdownHook(this);
        Signal sigTERM = new Signal("TERM");/* 注册KILL信号 */
        Signal sigINT = new Signal("INT");/* 注册CTRL+C信号 */
        Signal.handle(sigTERM, this);
        Signal.handle(sigINT, this);
    }

    @Override
    public void run() {
        App.instance().create();
    }

    @Override
    public void handle(Signal signal) {
        App.instance().destroy();
    }

    public static void main(String[] args) {
        new Boot().boot();
    }

}



