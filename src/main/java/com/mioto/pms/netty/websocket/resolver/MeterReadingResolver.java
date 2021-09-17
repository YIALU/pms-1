package com.mioto.pms.netty.websocket.resolver;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.mioto.pms.exception.WebSocketException;
import com.mioto.pms.module.device.model.Device;
import com.mioto.pms.module.device.service.IDeviceService;
import com.mioto.pms.netty.ChannelUtil;
import com.mioto.pms.netty.tcp.TcpHelper;
import com.mioto.pms.netty.websocket.model.MeterReadingMsgBody;
import com.mioto.pms.netty.websocket.model.ReceiveMsg;
import com.mioto.pms.result.WsSystemTip;
import com.mioto.pms.utils.SpringBeanUtil;
import io.netty.channel.ChannelHandlerContext;

import java.util.List;

/**
 * @author admin
 * @date 2021-09-01 16:04
 */
public class MeterReadingResolver extends WsMessageResolver{

    @Override
    protected void resolve(ChannelHandlerContext ctx, ReceiveMsg msg) {
        MeterReadingMsgBody body = JSONUtil.parseObj(msg.getBody()).toBean(MeterReadingMsgBody.class);
        List<Device> deviceList = SpringBeanUtil.getBean(IDeviceService.class).findByRoomId(body.getRoomId());
        if (CollUtil.isNotEmpty(deviceList)){
            for (Device device : deviceList) {
                if (StrUtil.isEmpty(device.getFocus()) || !ChannelUtil.containsKey(device.getFocus())){
                    throw new WebSocketException(WsSystemTip.DEVICE_OFFLINE);
                }
                TcpHelper.meterReading(device.getLine(),device.getFocus());
                ChannelUtil.putTcpWsRelation(ChannelUtil.getCtx(device.getFocus()),ctx);
            }
        }
    }
}
