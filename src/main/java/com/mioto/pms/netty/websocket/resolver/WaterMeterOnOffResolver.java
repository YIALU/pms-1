package com.mioto.pms.netty.websocket.resolver;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.mioto.pms.exception.WebSocketException;
import com.mioto.pms.module.device.service.IDeviceService;
import com.mioto.pms.netty.ChannelUtil;
import com.mioto.pms.netty.tcp.TcpHelper;
import com.mioto.pms.netty.websocket.model.ReceiveMsg;
import com.mioto.pms.netty.websocket.model.WaterMeterOnOffMsgBody;
import com.mioto.pms.result.WsSystemTip;
import com.mioto.pms.utils.SpringBeanUtil;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author admin
 * @date 2021-09-01 19:01
 */
public class WaterMeterOnOffResolver extends WsMessageResolver{

    @Override
    protected void resolve(ChannelHandlerContext ctx, ReceiveMsg msg) {
        WaterMeterOnOffMsgBody body = JSONUtil.parseObj(msg.getBody()).toBean(WaterMeterOnOffMsgBody.class);
        if (StrUtil.isEmpty(body.getFocus()) || !ChannelUtil.containsKey(body.getFocus())){
            throw new WebSocketException(WsSystemTip.DEVICE_OFFLINE);
        }
        TcpHelper.waterMeterOnOffControl(body.isSwitchStatus(),body.getConverterAddress(),body.getDeviceId(),body.getFocus());
        //ChannelUtil.putTcpWsRelation(ChannelUtil.getCtx(body.getFocus()),ctx);
        SpringBeanUtil.getBean(IDeviceService.class).updateOnOffStatusByDeviceIdAndFocus(body.getDeviceId(),body.getFocus(),body.isSwitchStatus()?0:1);
    }
}
