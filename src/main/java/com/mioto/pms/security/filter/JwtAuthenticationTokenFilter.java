package com.mioto.pms.security.filter;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.mioto.pms.module.user.model.User;
import com.mioto.pms.module.weixin.model.MiniProgramUser;
import com.mioto.pms.result.ResultData;
import com.mioto.pms.result.SystemTip;
import com.mioto.pms.security.config.JwtConfig;
import com.mioto.pms.utils.ResultUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * JWT接口请求校验拦截器
 * 请求接口时会进入这里验证Token是否合法和过期
 *
 * @author mioto-qinxj
 * @date 2019/12/4
 */
@Slf4j
public class JwtAuthenticationTokenFilter extends BasicAuthenticationFilter {

    public JwtAuthenticationTokenFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 获得TokenHeader
        String tokenHeader = request.getHeader(JwtConfig.tokenHeader);
        if (StrUtil.isNotEmpty(tokenHeader) && tokenHeader.startsWith(JwtConfig.tokenPrefix)) {
            try {
                // 截取JWT前缀
                String token = tokenHeader.substring(JwtConfig.tokenPrefix.length());
                // 解析JWT
                Claims claims = Jwts.parser().setSigningKey(JwtConfig.secret).parseClaimsJws(token).getBody();
                // 获取用户名
                String subject = claims.getSubject();
                String userId = claims.getId();

                if (StrUtil.isNotEmpty(subject) && StrUtil.isNotEmpty(userId)) {
                    Object obj = claims.get("logonUser");
                    //后台用户登录
                    if (StrUtil.equals(subject,"webUser")) {
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(Convert.convert(User.class, obj), claims.get("role"), CollUtil.newHashSet());
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }else if (StrUtil.equals(subject,"miniProgramUser")){
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(Convert.convert(MiniProgramUser.class, obj), claims.get("role"), CollUtil.newHashSet());
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            } catch (ExpiredJwtException e) {
                log.error("token expired");
                ResultUtil.responseJson(response, ResultData.result(SystemTip.TOKEN_EXPIRED));
                return;
            } catch (Exception e) {
                log.error("token verify");
                ResultUtil.responseJson(response, ResultData.result(SystemTip.TOKEN_VERIFY));
                return;
            }

        }
        chain.doFilter(request, response);
    }
}
