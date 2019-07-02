package com.yootk.ssm.realm;

import com.yootk.ssm.service.IMemberPrivilegeService;
import com.yootk.ssm.service.IMemberService;
import com.yootk.ssm.vo.Member;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.Set;

public class MemberRealm extends AuthorizingRealm {

    @Autowired
    private IMemberService memberService ;
    @Autowired
    private IMemberPrivilegeService memberPrivilegeService ;
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {
        System.out.println("【2】{MemberRealm}**************用户授权处理*************");
        Map<String, Set<String>> map = this.memberPrivilegeService.getByMember((String)principal.getPrimaryPrincipal());
        //将所有获取的授权信息保存在AuthorizationInfo类之中
        SimpleAuthorizationInfo authz = new SimpleAuthorizationInfo() ; //返回的授权信息
        authz.setRoles(map.get("allRoles"));
        authz.setStringPermissions(map.get("allActions"));
        return authz;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("【1】{MemberRealm} =========用户认证处理============");
        String mid = (String)token.getPrincipal() ;
        Member member = this.memberService.get(mid) ;   //根据mid查询用户信息
        if(member == null){
            throw new UnknownAccountException(mid + "账户信息不存在") ;
        }
        String password = new String((char[]) token.getCredentials()) ;
        if(!member.getPassword().equals(password)) {    //如果密码不同
            throw new IncorrectCredentialsException("错误的用户名或密码") ;
        }
        if(member.getLocked().equals(1)) {  //如果用户是锁定状态
            throw new LockedAccountException(mid + "账户已经别锁定") ;
        }
        return new SimpleAuthenticationInfo(token.getPrincipal(),token.getCredentials(),this.getName()) ;

    }
}
