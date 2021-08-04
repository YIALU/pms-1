package com.mioto.pms.netty.protocol;

import cn.hutool.core.text.StrFormatter;
import com.mioto.pms.netty.protocol.acquisition.AcquisitionServiceImpl;
import com.mioto.pms.utils.SpringBeanUtil;

import java.util.Arrays;

/**
 * @author admin
 * @date 2021-06-01 11:08
 */
public enum PortEnum {
    /**
     * 采集终端
     */
    ACQUISITION_TERMINAL(9008,true, AcquisitionServiceImpl.class);

    public static ProtocolService getServiceByPort(int port){
        Class<? extends ProtocolService> clazz = Arrays.stream(PortEnum.values()).filter(portEnum -> portEnum.getPort() == port)
                .findFirst().get().getProtocol();
        return SpringBeanUtil.getBean(clazz);
    }

    public static PortEnum getInstance(int port) {
        for (PortEnum value : PortEnum.values()) {
            if (value.port == port){
                return value;
            }
        }
        throw new RuntimeException(StrFormatter.format("port - {} undefined", port));
    }

    PortEnum(int port,boolean isHex,Class<? extends ProtocolService> clazz) {
        this.port = port;
        this.isHex=isHex;
        this.clazz = clazz;
    }
    private boolean isHex;

    public boolean isHex() {
        return isHex;
    }

    private int port;
    private Class<? extends ProtocolService> clazz;

    public int getPort() {
        return port;
    }

    public Class<? extends ProtocolService> getProtocol() {
        return clazz;
    }
}
