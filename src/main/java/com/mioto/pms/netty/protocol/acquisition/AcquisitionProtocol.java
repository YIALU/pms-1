package com.mioto.pms.netty.protocol.acquisition;

import com.mioto.pms.netty.protocol.Protocol;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author admin
 * @date 2021-06-01 11:21
 */
@Getter
@Setter
public class AcquisitionProtocol implements Protocol {
    private String startCharacter = "68";
    private String len;
    private String controlDomain;
    private String addressDomain;
    private ApplicationLayer applicationLayer;
    private String checkSum;
    private String endCharacter = "16";
}
