package com.mioto.pms.security.utils;

import cn.hutool.core.collection.CollUtil;
import com.mioto.pms.module.user.model.User;
import com.mioto.pms.module.weixin.model.MiniProgramUser;
import com.mioto.pms.security.config.JwtConfig;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Date;

/**
 * jwt工具类
 *
 * @author mioto-qinxj
 * @date 2019/12/4
 */
@Slf4j
public class JwtTokenUtil {
    /**
     * 生成token
     * @param
     * @return
     */
    public static String createAccessToken(User user, Collection<? extends GrantedAuthority> authorities) {
        //登录成功生成token
        String token = Jwts.builder()
                .setId(String.valueOf(user.getId()))
                //主题
                .setSubject("webUser")
                //签发时间
                .setIssuedAt(new Date())
                //签发者
                .setIssuer("mioto")
                .claim("logonUser",user)
                //自定义属性 放入用户角色
                .claim("role",authorities.stream().findFirst().get())
                //失效时间
                .setExpiration(new Date(System.currentTimeMillis() + JwtConfig.expiration))
                //签名算法和密钥
                .signWith(SignatureAlgorithm.HS512, JwtConfig.secret)
                .compact();
        return JwtConfig.tokenPrefix + token;
    }


    /**
     * 生成token
     * @param
     * @return
     */
    public static String createAccessToken(MiniProgramUser miniProgramUser,Integer userType) {
        //登录成功生成token
        String token = Jwts.builder()
                .setId(String.valueOf(miniProgramUser.getUserId()))
                //主题
                .setSubject("miniProgramUser")
                //签发时间
                .setIssuedAt(new Date())
                //签发者
                .setIssuer("mioto")
                .claim("logonUser",miniProgramUser)
                .claim("role",userType)
                //失效时间
                .setExpiration(new Date(System.currentTimeMillis() + JwtConfig.expiration))
                //签名算法和密钥
                .signWith(SignatureAlgorithm.HS512, JwtConfig.secret)
                .compact();
        return JwtConfig.tokenPrefix + token;
    }
}
