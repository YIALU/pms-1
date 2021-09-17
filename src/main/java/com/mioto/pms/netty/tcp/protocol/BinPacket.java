package com.mioto.pms.netty.tcp.protocol;

import java.nio.ByteBuffer;

/**
 * Created by PETER on 2015/3/13.
 */
public class BinPacket {
    private ByteBuffer byteBuffer;

    public ByteBuffer getByteBuffer() {
        return byteBuffer;
    }

    public void setByteBuffer(ByteBuffer byteBuffer) {
        this.byteBuffer = byteBuffer;
    }

}
