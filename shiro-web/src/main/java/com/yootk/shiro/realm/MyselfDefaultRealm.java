package com.yootk.shiro.realm;

import org.apache.shiro.authc.*;
import org.apache.shiro.realm.Realm;

public class MyselfDefaultRealm implements Realm {
    private static int count = 0 ;
    @Override
    public String getName() {   //获取Realm的名称
        return "my-happy-realm -" + count++;
    }

    @Override
    public boolean supports(AuthenticationToken token) {    //判断当前的Token是否可以使用此Realm
        return token instanceof UsernamePasswordToken;  //当前Realm只支持“UsernamePasswordToken”类型
    }

    @Override
    public AuthenticationInfo getAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String mid = (String) token.getPrincipal() ;    //获取用户名
        String password = new String((char[]) token.getCredentials()) ;//获取密码
        if(!"lee".equals(mid)) {    //此时用户名不存在
            throw new UnknownAccountException("【"+mid+"】该用户信息不存在，请确认输入的用户名");
        }
        if(!"hello".equals(mid)) {  //密码不正确
            throw new IncorrectCredentialsException("错误的用户名或密码") ;
        }
        return new SimpleAuthenticationInfo(token.getPrincipal(),token.getCredentials(),this.getName()) ;
    }
}
