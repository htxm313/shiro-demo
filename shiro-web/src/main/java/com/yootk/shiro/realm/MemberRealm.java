package com.yootk.shiro.realm;

import com.yootk.shiro.service.IMemberService;
import com.yootk.shiro.service.impl.MemberServiceImpl;
import com.yootk.shiro.vo.Member;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.Map;
import java.util.Set;

public class MemberRealm extends AuthorizingRealm {
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("【MemberRealm】============用户授权处理============");
        IMemberService memberService = new MemberServiceImpl() ;
        //根据用户名查询权限
        Map<String, Set<String>> map = memberService.findPrivilegeByMember((String) principals.getPrimaryPrincipal()) ;
        //将所有获取的授权信息保存在AuthorizationInfo类的实例之中
        SimpleAuthorizationInfo authz = new SimpleAuthorizationInfo() ; //返回的授权信息
        authz.setRoles(map.get("allRoles"));
        authz.setStringPermissions(map.get("allActions"));
        return authz;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //本初的程序要进行用户认证的处理操作
        System.out.println("【MemberRealm】==========用户认证处理==========");
        String mid = (String) token.getPrincipal() ;
        IMemberService memberService = new MemberServiceImpl() ; //获取业务接口实例
        Member member = memberService.get(mid) ;    //根据mid查询用户信息
        if(member == null) {    //用户信息不存在
            throw new UnknownAccountException(mid+ "账户信息不存在") ;
        }
        String password = new String((char[]) token.getCredentials()) ;
        if(!member.getPassword().equals(password)){ //密码不同
            throw new IncorrectCredentialsException("错误的用户名或密码") ;
        }
        if(member.getLocked().equals(1)){   //用户锁定了
            throw new LockedAccountException(mid + "账户已锁定") ;
        }
        return new SimpleAuthenticationInfo(token.getPrincipal(),token.getCredentials(),this.getName()) ;
    }

}
