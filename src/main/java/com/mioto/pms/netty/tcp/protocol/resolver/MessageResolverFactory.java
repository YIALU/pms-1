package com.mioto.pms.netty.tcp.protocol.resolver;

import java.util.HashMap;
import java.util.Map;

/**
 * @author admin
 * @date 2021-07-03 18:10
 */
public class MessageResolverFactory {
    private static Map<String, AbstractMessageResolver> map = new HashMap<>();
    static {
        map.put("login",new LoginResolver());
        map.put("heartbeat",new HeartbeatResolver());
        map.put("getCurrentPositiveActiveEnergyValue", new CurrentPositiveActiveEnergy());
        map.put("getOnoffStatusAndRecord", new OnoffStatusAndRecordResolver());
        map.put("getOnoffStatusAndRecord", new OnoffStatusAndRecordResolver());
    }

    public static AbstractMessageResolver getMessageResolver(String command){
        if (map.containsKey(command)){
            return map.get(command);
        }
        throw new RuntimeException("unsupported protocol type { "+command+" }");
    }
}
