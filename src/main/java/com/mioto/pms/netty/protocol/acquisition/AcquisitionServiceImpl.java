package com.mioto.pms.netty.protocol.acquisition;

import com.mioto.pms.netty.protocol.Protocol;
import com.mioto.pms.netty.protocol.ProtocolService;
import org.springframework.stereotype.Component;

/**
 * @author admin
 * @date 2021-06-01 11:17
 */
@Component
public class AcquisitionServiceImpl implements ProtocolService {
    @Override
    public Protocol protocolHandler(String data) {
        return AcquisitionProtocolHelper.parse(data);
    }

    @Override
    public String toProtocol(Protocol protocol) {
        return null;
    }
}
