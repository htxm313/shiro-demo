package com.yootk.ssm.service;

import java.util.Map;
import java.util.Set;

public interface IMemberPrivilegeService {

    /**
     * 根据用户编号获取该用户对应的角色与权限信息
     * @param mid   用户编号
     * @return  包括
     * 1、key = allRoles、value = 所有角色ID的Set集合
     * 2、key = allActions、value = 所有权限ID的Set集合
     */
    public Map<String, Set<String>> getByMember(String mid) ;
}
