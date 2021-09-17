package com.mioto.pms.netty.websocket.resolver;

import cn.hutool.core.util.StrUtil;
import com.mioto.pms.exception.WebSocketException;
import com.mioto.pms.netty.ChannelUtil;
import com.mioto.pms.netty.websocket.WsClientType;
import com.mioto.pms.netty.websocket.model.CmdType;
import com.mioto.pms.netty.websocket.model.ReceiveMsg;
import com.mioto.pms.result.WsSystemTip;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author admin
 * @date 2021-08-30 16:02
 */
public abstract class WsMessageResolver {

    public void process(ChannelHandlerContext ctx, ReceiveMsg msg){
        dataVerify(msg);
        authVerify(msg);
        resolve(ctx,msg);

    }

    protected abstract void resolve(ChannelHandlerContext ctx, ReceiveMsg msg);

    /**
     * 连接是否完成认证
     * @param msg
     * @return
     */
    private void authVerify(ReceiveMsg msg){
        if (ChannelUtil.containsWsKey(msg.getClientType(),msg.getUserId()) || msg.getCmd() == CmdType.CMD_AUTH){
            return;
        }
        throw new WebSocketException(CmdType.CMD_REPLY, WsSystemTip.AUTH_ERROR);
    }


    /**
     * 客户端类型验证
     * @param msg
     * @return
     */
    private void dataVerify(ReceiveMsg msg){
        if (StrUtil.equals(msg.getClientType(), WsClientType.TYPE_WEB) || StrUtil.equals(msg.getClientType(), WsClientType.TYPE_MINI_PROGRAM)){
            return;
        }
        throw new WebSocketException(CmdType.CMD_REPLY, WsSystemTip.CLIENT_TYPE_ERROR);
    }
}
