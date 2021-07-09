package com.spring.SecurityConfig;

import com.alibaba.fastjson.JSONObject;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class MyFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        JSONObject jsonObject = new JSONObject();
        response.setStatus(200);
        response.setContentType("application/json;charset=UTF-8");
        System.out.println("登录失败");
        jsonObject.put("loginStatus","false");
        jsonObject.put("reason","账号密码错误!");
        response.getWriter().append(jsonObject.toString());
    }
}
