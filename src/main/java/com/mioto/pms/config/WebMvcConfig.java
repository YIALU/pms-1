package com.mioto.pms.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author mioto-qinxj
 * @date 2019/11/15
 */

/**
 * SpringBoot全局支持CORS（跨源请求）的配置
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //设置允许跨域的路径
        registry.addMapping("/**")
                //设置允许跨域请求的域名
                .allowedOrigins("*")
                //是否允许cookie
                .allowCredentials(true)
                //设置允许的方法
                .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS")
                //跨域允许时间
                .maxAge(3600);
    }
}
