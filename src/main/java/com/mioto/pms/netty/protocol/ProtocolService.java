package com.mioto.pms.netty.protocol;

/**
 * @author admin
 * @date 2021-06-01 11:13
 */
public interface ProtocolService {
    /**
     * 协议处理
     * @param data
     * @return
     */
    Protocol protocolHandler(String data);

    /**
     * 实体类转换协议体
     * @param protocol
     * @return
     */
    String toProtocol(Protocol protocol);
}
