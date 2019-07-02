package com.yootk.ssm.action;

import com.yootk.ssm.service.IDeptService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CommonAction {


    @Autowired
    private IDeptService deptService ;


    @RequestMapping("/logoutInfo")
    public String loing(){
        return "logout_info" ;
    }

    @RequestMapping("/login")
    public String login() {

        return "login" ;
    }

    //    @RequestMapping("/noauthz")
//    public String noauthz() {
//        return "plugins/noauthz" ;
//    }
    @RequiresRoles("member")    //拥有member角色才可以访问
    @RequiresPermissions("dept:add") //拥有指定的权限才可以访问
    // @RequiresAuthentication //必须认证之后才可以进行访问
    @RequestMapping("/pages/welcome")
    public String welcome() {
        this.deptService.list();
        return "welcome" ;
    }
}
