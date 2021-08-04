package com.mioto.pms.netty;

import io.netty.channel.ChannelHandlerContext;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 通道缓存
 * @author qinxj
 * @date 2020/11/9 20:06
 */
public class ChannelUtil {
    private static ConcurrentMap<String, ChannelHandlerContext> map = new ConcurrentHashMap(6);

    public static void put(String ip, ChannelHandlerContext ctx){
        map.put(ip,ctx);
    }

    public static ChannelHandlerContext getChannelHandlerContext(String ip){
        return map.get(ip);
    }

    public static void remove(String ip){
        map.remove(ip);
    }
}
