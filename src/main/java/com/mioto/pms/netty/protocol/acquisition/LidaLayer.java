package com.mioto.pms.netty.protocol.acquisition;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

/**
 * 链路接口检测 - 用于交换网络传输信道
 * @author admin
 * @date 2021-06-01 12:04
 */
@Getter
@Setter
@Slf4j
public class LidaLayer extends ApplicationLayer{
    /**
     * 数据单元标识
     */
    private String dataUnitMake;
    /**
     * 数据单元
     */
    private String dataUnit;

    @Override
    public void parse(String message) {
        log.info("链路接口检测 - {}",message);
    }
}
