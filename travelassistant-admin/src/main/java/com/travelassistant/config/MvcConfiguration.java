package com.travelassistant.config;

import com.travelassistant.interceptors.LoginProtectInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * projectName: personblog
 *
 * @author: 邱绍峰
 * time: 2024/03/8 22:43 周六
 * description: mvc相关配置
 */
@Configuration
public class MvcConfiguration implements WebMvcConfigurer {

    /**
     * 用户注册拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //拦截后台管理模块的路径  排除登录和资源路径
        registry.addInterceptor(new LoginProtectInterceptor()).addPathPatterns("/**")
                .excludePathPatterns("/","/index.html","/index","/static/**",
                        "/user/login", "/user/logout",
                        "/api/**", "/css/**", "/images/**",
                        "/js/**", "/lib/**","/captcha"
                );
    }
}
