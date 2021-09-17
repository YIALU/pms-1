package com.mioto.pms.netty.websocket.resolver;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.mioto.pms.exception.WebSocketException;
import com.mioto.pms.netty.ChannelUtil;
import com.mioto.pms.netty.tcp.TcpHelper;
import com.mioto.pms.netty.websocket.model.OnoffStatusMsgBody;
import com.mioto.pms.netty.websocket.model.ReceiveMsg;
import com.mioto.pms.result.WsSystemTip;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author admin
 * @date 2021-09-02 15:29
 */
public class OnoffStatusResolver extends WsMessageResolver{
    @Override
    protected void resolve(ChannelHandlerContext ctx, ReceiveMsg msg) {
        OnoffStatusMsgBody body = JSONUtil.parseObj(msg.getBody()).toBean(OnoffStatusMsgBody.class);
        if (StrUtil.isEmpty(body.getFocus()) || !ChannelUtil.containsKey(body.getFocus())){
            throw new WebSocketException(WsSystemTip.DEVICE_OFFLINE);
        }
        TcpHelper.getSwitchStatus(body.getLine(),body.getFocus());
        ChannelUtil.putTcpWsRelation(ChannelUtil.getCtx(body.getFocus()),ctx);
    }
}
