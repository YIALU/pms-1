package com.mioto.pms.netty.tcp;

import cn.hutool.core.util.HexUtil;
import com.mioto.pms.netty.ChannelUtil;
import com.mioto.pms.netty.tcp.protocol.BinPacket;
import com.mioto.pms.netty.tcp.protocol.Packet;
import com.mioto.pms.netty.tcp.protocol.ProtocalManagerHelper;
import com.mioto.pms.netty.tcp.protocol.internal.wrap.TrunOnOff;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * @author qinxj
 * @date 2020/11/6 14:52
 */
@Slf4j
public class TcpHelper {

	/**
	 * 电表开合闸控制
	 * @param switchStatus true-开 false-关
	 * @param meterAddress
	 * @param terminalAddress
	 */
	public static void ammeterOnOffControl(boolean switchStatus,String meterAddress,String terminalAddress){
		Map<String,Object> data=new HashMap<>(1);
		final String command = switchStatus ? "Forward_SwitchOn" : "Forward_SwitchOff";
		data.put("dataContent", TrunOnOff.getTurnOnOffCmd(switchStatus, meterAddress));
		control(data,command,terminalAddress,0);
	}

	/**
	 * 水表开合闸控制
	 * @param switchStatus
	 * @param converterAddress
	 * @param meterAddress
	 * @param terminalAddress
	 */
	public static void waterMeterOnOffControl(boolean switchStatus,String converterAddress,String meterAddress,String terminalAddress){
		Map<String,Object> data=new HashMap<>(1);
		final String command = switchStatus ? "Forward_WaterMeter_SwitchOn" : "Forward_WaterMeter_SwitchOff";
		data.put("dataContent",TrunOnOff.getWaterMeterTurnOnOffCmd(switchStatus, converterAddress,meterAddress));
		control(data,command,terminalAddress,0);
	}

	/**
	 * 获取通断电状态及纪录
	 * @param line
	 * @param terminalAddress
	 */
	public static void getSwitchStatus(int line,String terminalAddress){
		final String command = "getOnoffStatusAndRecord";
		control(new HashMap<>(0),command,terminalAddress,line);
	}

	/**
	 * 抄表
	 * @param line 序号
	 * @param terminalAddress 采集器地址
	 */
	public static void meterReading(int line,String terminalAddress){
		final String command = "getCurrentPositiveActiveEnergyValue";
		control(new HashMap<>(0),command,terminalAddress,line);
	}

	private static void control(Map<String,Object> data,String command,String terminalAddress,int line){
		terminalAddress = terminalAddress.toUpperCase();
		Packet packet =new Packet();
		packet.setCommand(command);
		packet.setData(data);
		packet.setTerminalAddress(terminalAddress);
		packet.setLine(line);
		BinPacket binPacket =new BinPacket();
		try {
			ProtocalManagerHelper.getProtocalManagerRgm().encode(packet,binPacket);
			sendMessage(ChannelUtil.getCtx(terminalAddress),binPacket);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    public static void sendMessage(ChannelHandlerContext ctx, BinPacket packet){
        ByteBuf buffer = Unpooled.buffer();
        byte[] data  = packet.getByteBuffer().array();
        buffer.writeBytes(data);
        if(ctx != null){
			log.info("send message - {} to - {}",HexUtil.encodeHexStr(data),ctx.channel().remoteAddress());
        	ctx.writeAndFlush(buffer).addListener((ChannelFutureListener) future -> {
        		 if (!future.isSuccess()){
         	        log.error("message send fail");
         	    }
         	    else{
         	        log.warn("message send success");
         	    }
        	});
        }else{
			log.error("Ctx is NULL.");
        }
    }
}
