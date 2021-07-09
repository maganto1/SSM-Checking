package com.spring.SecurityConfig;

import com.alibaba.fastjson.JSONObject;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class MySuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        JSONObject jsonObject = new JSONObject();
        response.setStatus(200);
        response.setContentType("application/json;charset=UTF-8");
        System.out.println("成功");
        jsonObject.put("loginStatus","true");
        response.getWriter().append(jsonObject.toString());
    }
}
