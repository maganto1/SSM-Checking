package com.spring.SecurityConfig;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

public class SecurityWebApplicationInitializer implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        // 创建AnnotationConfigWebApplicationContext
        AnnotationConfigWebApplicationContext springContext = new AnnotationConfigWebApplicationContext();
        // spring的配置类  springSecurity的配置类
        springContext.register(sercurity.class);
    }
}
