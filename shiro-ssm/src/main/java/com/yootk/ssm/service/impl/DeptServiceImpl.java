package com.yootk.ssm.service.impl;

import com.yootk.ssm.service.IDeptService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Service;

@Service
public class DeptServiceImpl implements IDeptService {
    @Override
    @RequiresRoles("member") //拥有member角色才可以访问
    @RequiresPermissions("dept:add")    //拥有指定的权限才可以访问
    public void list() {
        System.out.println("**********(部门列表)*************");

    }
}
