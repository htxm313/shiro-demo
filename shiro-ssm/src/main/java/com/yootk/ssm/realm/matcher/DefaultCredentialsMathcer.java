package com.yootk.ssm.realm.matcher;

import com.yootk.ssm.util.EncryptUtil;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

public class DefaultCredentialsMathcer extends SimpleCredentialsMatcher {
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        //此处方法主要进行密码的匹配操作，这里面需要接收认证的Token信息
        //1、通过Token获取用户输入的密码内容
        Object tokenPassword = EncryptUtil.encode(super.toString(token.getCredentials())) ;
        //2、获取认证之后的密码内容
        Object infoPassword = super.getCredentials(info) ;
        return super.equals(tokenPassword,infoPassword) ;
    }
}
