package com.zxj.finalexam.config;

import com.zxj.finalexam.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
//@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private LoginInterceptor loginInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor).addPathPatterns("/finalexam/**");//.excludePathPatterns("/img/**");//**拦截所有  *.do拦截以do结尾的请求
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
