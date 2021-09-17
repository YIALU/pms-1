package com.mioto.pms.netty.tcp.protocol.internal.wrap;

import com.mioto.pms.netty.tcp.protocol.exception.VerifyException;
import org.apache.commons.codec.binary.Hex;

import java.nio.ByteBuffer;
import java.util.Calendar;

public class TrunOnOff {
	
	public static String getTurnOnOffCmd(boolean ison,String addr) {
		byte on = 0x4f;
		byte off = 0x4d;
		
		byte control = ison ? on : off;
		
		
		ByteBuffer tailBuffer = ByteBuffer.allocate(28);
		tailBuffer.put(new byte[]{0x68});
		try {
			tailBuffer.put(addrReverseOrder(addr));
			tailBuffer.put(new byte[]{0x68,0x1c,0x10});
			tailBuffer.put(new byte[]{0x35,(byte) 0x89,0x67,0x45,0x35,0x33,0x33});
			tailBuffer.put(new byte[]{0x33,control,0x33});

			Calendar calendar = Calendar.getInstance();
			byte[] time = new byte[6];
			time[0]=(byte) (Integer.parseInt(getSec(calendar)) + 0x33);
			time[1]=(byte) (Integer.parseInt(getMin(calendar),16) + 0x33);
			time[2]=(byte) (Integer.parseInt(getHour(calendar),16) + 0x33);
			time[3]=(byte) (Integer.parseInt(getDay(calendar),16) + 0x33);
			time[4]=(byte) (Integer.parseInt(getMonth(calendar),16) + 0x33);
			time[5]=(byte) (Integer.parseInt(getYear(calendar),16) + 0x33);

			tailBuffer.put(time);
			tailBuffer.put(new byte[]{cs(tailBuffer)});
			tailBuffer.put(new byte[]{0x16});
			byte[] cmds = tailBuffer.array();
			return String.valueOf(Hex.encodeHex(reverseOrderByte(cmds)));
		} catch (VerifyException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	
	public static String getWaterMeterTurnOnOffCmd(boolean ison,String converterAddress,String meterAddress) {
		byte[] off = new byte[]{(byte) 0x99,0x47,0x16};
		byte[] on = new byte[]{0x55,0x03,0x16};
		byte[] control = ison ? on : off;
		ByteBuffer tailBuffer = ByteBuffer.allocate(34);
		tailBuffer.put(new byte[]{0x68});
		try {
			tailBuffer.put(addrReverseOrder(converterAddress));
			tailBuffer.put(new byte[]{0x68,0x1b,0x16,0x03,(byte) 0xfe,(byte) 0xfe,(byte) 0xfe,(byte) 0xfe});
			tailBuffer.put(new byte[]{0x68,0x10});
			tailBuffer.put(addrReverseOrder(meterAddress));
			tailBuffer.put(new byte[]{0x00,0x04,0x04,(byte) 0xa0,0x17,0x00});
			tailBuffer.put(control);

			tailBuffer.put(new byte[]{cs(tailBuffer)});
			tailBuffer.put(new byte[]{0x16});
			byte[] cmds = tailBuffer.array();
			return String.valueOf(Hex.encodeHex(reverseOrderByte(cmds)));
		} catch (VerifyException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	private static byte[] addrReverseOrder(String addr) throws VerifyException {
		//202104060201
		byte[] olds = strsToByteHexs(addr);

		return reverseOrderByte(olds);
	}
	
	private static byte[] reverseOrderByte(byte[] cmds) {
		// TODO Auto-generated method stub
		int length = cmds.length;
		byte[] newbytes = new byte[length]; 
		for(int i = 0;i<length;i++){
			newbytes[i] = cmds[length-i-1];
		}
		
		return newbytes;
	}

	private static char[] reverseOrderChar(char[] cmds) {
		// TODO Auto-generated method stub
		int length = cmds.length;
		char[] newchars = new char[length]; 
		for(int i = 0;i<length;i++){
			newchars[i] = cmds[length-i-1];
		}
		
		return newchars;
	}
	
	
	private static byte[] strsToByteHexs(String source) throws VerifyException {
		byte[] bytes = null;
		int length = source.length() / 2;
		if(source.length()%2 != 0){
			throw new VerifyException(1111,"Data Error.");
		}else{
			bytes = new byte[length];
			String temp;
			for(int i = 0;i<length;i++){
				temp = source.substring(i*2, i*2+2);
				bytes[i] = (byte) Integer.parseInt(temp, 16);
			}
		}
		
		return bytes;
	}

	private static String getYear(Calendar calendar){
		int year = calendar.get(Calendar.YEAR);
		return String.valueOf(Integer.valueOf(String.valueOf(year).substring(2, 4)));
	}
	private static String getDay(Calendar calendar){
		int hour = calendar.get(Calendar.DAY_OF_MONTH);
		return String.valueOf(Integer.valueOf(hour));
	}
	private static String getMonth(Calendar calendar){
		int month = calendar.get(Calendar.MONTH) + 1;
		return String.valueOf(Integer.valueOf(month));
	}
	private static String getHour(Calendar calendar){
		int hour = calendar.get(Calendar.HOUR_OF_DAY) + 1;
		return String.valueOf(Integer.valueOf(hour));
	}
	private static String getMin(Calendar calendar){
		int Min = calendar.get(Calendar.MINUTE);
		return String.valueOf(Integer.valueOf(Min));
	}
	private static String getSec(Calendar calendar){
		int Sec = calendar.get(Calendar.SECOND);
		return String.valueOf(Integer.valueOf(Sec));
	}
	
	private static byte cs(ByteBuffer calculations){
		byte cs = 0;
		byte[] datas = calculations.array();
		for(byte b : datas){
			cs += b;
		}
		return cs;
	}

}
