package com.yootk.ssm.action;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MemberLoginAction {
    @RequestMapping("login_handle")
    public ModelAndView loginHandle(String mid,String password) {
        ModelAndView mav = new ModelAndView("forward:/login.action") ;
        AuthenticationToken token = new UsernamePasswordToken(mid,password) ;
        try{
            SecurityUtils.getSubject().login(token);
            mav.setViewName("forward:/pages/welcome.action");
        }catch (Exception e){
            e.printStackTrace();
            mav.addObject("errors",e.getMessage()) ;
        }
        return mav ;
    }
}
