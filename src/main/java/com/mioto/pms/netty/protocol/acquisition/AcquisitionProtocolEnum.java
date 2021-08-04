package com.mioto.pms.netty.protocol.acquisition;

/**
 * @author admin
 * @date 2021-06-01 11:39
 */
public enum AcquisitionProtocolEnum {
    LEN(2,6),
    CONTROL_DOMAIN(12,14),
    ADDRESS_DOMAIN(14,24),
    AFN(24,26),
    SEQ(26,28);

    private int start;
    private int end;

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    AcquisitionProtocolEnum(int start, int end) {
        this.start = start;
        this.end = end;
    }
}
