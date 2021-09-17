package com.mioto.pms.security.config;


import com.mioto.pms.security.filter.BasicAuthenticationProcessingFilter;
import com.mioto.pms.security.filter.JwtAuthenticationTokenFilter;
import com.mioto.pms.security.filter.VerCodeAuthProcessingFilter;
import com.mioto.pms.security.handler.UserAuthAccessDeniedHandler;
import com.mioto.pms.security.handler.UserAuthenticationEntryPointHandler;
import com.mioto.pms.security.handler.UserLogoutSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * SpringSecurity核心配置类
 *
 * @author mioto-qinxj
 * @date 2019/12/4
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 自定义注销成功处理器
     */
    @Autowired
    private UserLogoutSuccessHandler userLogoutSuccessHandler;
    /**
     * 自定义暂无权限处理器
     */
    @Autowired
    private UserAuthAccessDeniedHandler userAuthAccessDeniedHandler;
    /**
     * 自定义未登录的处理器
     */
    @Autowired
    private UserAuthenticationEntryPointHandler userAuthenticationEntryPointHandler;


    @Value("${http.antMatchers}")
    private String antMatchers;

    /**
     * 配置security的控制逻辑
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                //不进行权限验证的请求或资源(从配置文件中读取)
                .antMatchers(antMatchers.split(",")).permitAll()
                //其他的需要登陆后才能访问
                .anyRequest().authenticated()
                .and()
                //配置未登录自定义处理类
                .httpBasic().authenticationEntryPoint(userAuthenticationEntryPointHandler)
                .and()
                //配置登出地址
                .logout()
                .logoutUrl("/logout")
                //配置用户登出自定义处理类
                .logoutSuccessHandler(userLogoutSuccessHandler)
                .and()
                //配置没有权限自定义处理类
                .exceptionHandling().accessDeniedHandler(userAuthAccessDeniedHandler)
                .and()
                // 取消跨站请求伪造防护
                .csrf().disable()
                // 允许跨域访问
                .cors();
                //.and()
                //.rememberMe();
        // 基于Token不需要session
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        // 禁用缓存
        http.headers().cacheControl();
        // 添加JWT过滤器
        http.addFilter(new JwtAuthenticationTokenFilter(authenticationManager()));
        http.addFilterBefore(basicAuthenticationProcessingFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(verCodeAuthProcessingFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public BasicAuthenticationProcessingFilter basicAuthenticationProcessingFilter() throws Exception {
        BasicAuthenticationProcessingFilter verCodeAuthProcessingFilter =new BasicAuthenticationProcessingFilter();
        verCodeAuthProcessingFilter.setAuthenticationManager(authenticationManagerBean());
        return verCodeAuthProcessingFilter;
    }

    @Bean
    public VerCodeAuthProcessingFilter verCodeAuthProcessingFilter() throws Exception {
        VerCodeAuthProcessingFilter verCodeAuthProcessingFilter =new VerCodeAuthProcessingFilter();
        verCodeAuthProcessingFilter.setAuthenticationManager(authenticationManagerBean());
        return verCodeAuthProcessingFilter;
    }
}
