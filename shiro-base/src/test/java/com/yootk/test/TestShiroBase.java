package com.yootk.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

public class TestShiroBase {

    public static final String USERNAME = "lee" ;   // 假设此内容为输入数据
    public static final String PASSWORD = "hello" ; //假设此内容为输入数据

    @Test
    public void testAuth() throws Exception {
        //1、创建一个SececurityMangager接口类的对象实例，使用子类为了设置realm
        DefaultSecurityManager securityManager = new DefaultSecurityManager() ;
        //2、所有的认证信息都保存在shiro.ini文件，这个文件存储在CLASSPATH目录下
        Realm realm = new IniRealm("classpath:shiro.ini") ;
        //3、此时的SecurityManager需要设置上具体的realm对象信息
        securityManager.setRealm(realm);
        //4、如果要想进行数据的认证处理，还需要获取SecurityUtils工具类
        SecurityUtils.setSecurityManager(securityManager);  //设置安全管理类实例
        //5、如果要继续用户认证处理，则首先一定要获取一个Subject（用户）
        Subject subject = SecurityUtils.getSubject() ;
        //6、利用一个专属的认证Token的结构包装输入的用户名与密码信息
        AuthenticationToken token = new UsernamePasswordToken(USERNAME,PASSWORD) ;
        //7、利用Subject实现最终的用户认证处理操作
        subject.login(token);   //进行认证操作
        System.out.println("用户名："+subject.getPrincipal());

    }
}
