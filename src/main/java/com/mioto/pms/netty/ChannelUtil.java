package com.mioto.pms.netty;

import cn.hutool.cache.impl.CacheObj;
import cn.hutool.cache.impl.TimedCache;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.StrBuilder;
import cn.hutool.core.util.StrUtil;
import io.netty.channel.ChannelHandlerContext;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 通道缓存
 * @author qinxj
 * @date 2020/11/9 20:06
 */
public class ChannelUtil {
    /**
     * tcp连接缓存
     */
    private static ConcurrentMap<String, ChannelHandlerContext> tcpMap = new ConcurrentHashMap(6);
    /**
     * webSocket连接缓存
     */
    private static ConcurrentMap<String, ChannelHandlerContext> wsMap = new ConcurrentHashMap(6);
    /**
     * websocket-tcp通讯关系缓存
     * 默认1分钟过期
     */
    private static TimedCache<String,String> tcpWsRelationCache = new TimedCache<>(60*1000);

    static {
        //启动定时任务，每2分钟检查一次过期
        tcpWsRelationCache.schedulePrune(2*60*1000);
    }

    public static void put(String terminalAddress, ChannelHandlerContext ctx){
        tcpMap.put(terminalAddress,ctx);
    }

    public static ChannelHandlerContext getCtx(String terminalAddress){
        return tcpMap.get(terminalAddress.toUpperCase());
    }

    public static boolean containsKey(String terminalAddress){
        if (StrUtil.isEmpty(terminalAddress)){
            return false;
        }
        return tcpMap.containsKey(terminalAddress.toUpperCase());
    }

    public static Set<String> getTerminalAddress(){
        if (CollUtil.isNotEmpty(tcpMap)){
            return tcpMap.keySet();
        }
        return new HashSet<>(0);
    }

    public static void remove(ChannelHandlerContext ctx){
        for (Map.Entry<String, ChannelHandlerContext> stringChannelHandlerContextEntry : tcpMap.entrySet()) {
            if (StrUtil.equals(getShortText(stringChannelHandlerContextEntry.getValue()),getShortText(ctx))){
                tcpMap.remove(stringChannelHandlerContextEntry.getKey());
                break;
            }
        }
    }


    public static void putWs(String clientType,int userId, ChannelHandlerContext ctx){
        wsMap.put(builderKey(clientType,userId),ctx);
    }

    public static boolean containsWsKey(String clientType,int userId){
        return wsMap.containsKey(builderKey(clientType,userId));
    }

    public static void putTcpWsRelation(ChannelHandlerContext tcpChannel,ChannelHandlerContext wsChannel){
        tcpWsRelationCache.put(getShortText(wsChannel),getShortText(tcpChannel));
    }

    public static List<ChannelHandlerContext> getWsChannelsByTcpChannel(ChannelHandlerContext tcpChannel){
        String shortText = getShortText(tcpChannel);
        Iterator<CacheObj<String, String>> iterator = tcpWsRelationCache.cacheObjIterator();
        CacheObj<String, String> cacheObj;
        List<ChannelHandlerContext> list = new ArrayList<>(2);
        while (iterator.hasNext()){
            cacheObj = iterator.next();
            if (StrUtil.equals(cacheObj.getValue(),shortText)){
                //一定时间内，可能存在多个客户端同时控制一种设备的情况
                for (Map.Entry<String, ChannelHandlerContext> stringChannelHandlerContextEntry : wsMap.entrySet()) {
                    if (StrUtil.equals(getShortText(stringChannelHandlerContextEntry.getValue()), cacheObj.getKey())) {
                        list.add(stringChannelHandlerContextEntry.getValue());
                    }
                }
                break;
            }
        }

        return list;
    }

    private static String getShortText(ChannelHandlerContext ctx){
        return ctx.channel().id().asShortText();
    }

    /**
     * key格式 - clientType_userId
     * @param clientType 客户端类型
     * @param userId 用户id
     * @return
     */
    private static String builderKey(String clientType,int userId){
        return StrBuilder.create(clientType).append(StrUtil.UNDERLINE).append(userId).toString();
    }
}
