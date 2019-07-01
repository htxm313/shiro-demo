package com.yootk.shiro.service;

import com.yootk.shiro.vo.Member;

import java.util.Map;
import java.util.Set;

public interface IMemberService {
    public Member get(String mid) ;

    /**
     * 根据用户名获取用户对应的所有角色和所有的权限信息
     * @param mid 用户名
     * @return  返回的内容包括
     * 1、key = allRoles、value = 所有角色的集合
     * 2、key = allActions 、value = 所有权限的集合
     */
    public Map<String, Set<String>> findPrivilegeByMember(String mid) ;
}
