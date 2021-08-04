package com.mioto.pms.netty.protocol.acquisition;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 链路用户数据 - 应用层
 * @author admin
 * @date 2021-06-01 11:26
 */
@Getter
@Setter
public abstract class ApplicationLayer {
    /**
     * 应 用 层 功 能 码
     */
    private String afn;
    /**
     * 帧序列域
     */
    private String seq;

    /**
     * 数据解析
     * @param message
     */
    public abstract void parse(String message);
}
