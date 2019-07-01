package com.yootk.shiro.servlet;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/login.servlet")
public class MemberLoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = "/login.jsp" ;    //设置一个跳转路径
        String mid = req.getParameter("mid") ;
        String password = req.getParameter("password") ;
        //在Shiro里面如果要进行认证处理则一定要提供有一个认证的Token信息
        AuthenticationToken token = new UsernamePasswordToken(mid,password) ;
        try {
            SecurityUtils.getSubject().login(token);
            path = "/pages/welcome.jsp";   //登录成功不出现异常
        }catch (Exception e){
            req.setAttribute("errors",e.getMessage());
        }
        req.getRequestDispatcher(path).forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req,resp);
    }
}
