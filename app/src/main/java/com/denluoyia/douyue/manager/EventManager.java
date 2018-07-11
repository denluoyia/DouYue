package com.denluoyia.douyue.manager;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

/**
 * Function:
 * Created by denluoyia on 2018/06/22
 */
public class EventManager {

    private static final Bus BUS = new Bus(ThreadEnforcer.MAIN);

    private static Bus getInstance(){return BUS;}

    public static void post(Object event){
        if (event == null) return;
        getInstance().post(event);
    }

    public static void register(Object obj){
        if (obj == null) return;
        getInstance().register(obj);
    }

    public static void unregister(Object obj){
        if (obj == null) return;
        getInstance().unregister(obj);
    }
}
