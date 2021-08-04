package com.mioto.pms.security.config;

/**
 * JWT配置类
 *
 * @author mioto-qinxj
 * @date 2019/12/4
 */
public class JwtConfig {
    /**
     * 密钥KEY
     */
    public static String secret = "mioto-secret-key";
    /**
     * TokenKey
     */
    public static final String tokenHeader = "Authorization";
    /**
     * Token前缀字符
     */
    public static String tokenPrefix = "Mioto-";

    /**
     * 过期时间 24 * 60 * 60 * 1000
     */
    public static Integer expiration = 24 * 60 * 60 * 1000;

}
