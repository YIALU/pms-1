package com.mioto.pms.netty.tcp.protocol;

import cn.hutool.core.util.ObjectUtil;

/**
 * @author admin
 * @date 2021-07-03 17:10
 */
public class ProtocalManagerHelper {

    private static IProtocal protocalManagerRgm;

    public static IProtocal getProtocalManagerRgm() {
        if (ObjectUtil.isEmpty(protocalManagerRgm)) {
            try {
                protocalManagerRgm = ProtocalManagerFactory.getProtocalManager("rgm_376_1");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return protocalManagerRgm;
    }
}
