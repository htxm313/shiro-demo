package com.yootk.ssm.service.impl;


import com.yootk.ssm.dao.IActionDAO;
import com.yootk.ssm.dao.IRoleDAO;
import com.yootk.ssm.service.IMemberPrivilegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
@Service
public class MemberPrivilegeServiceImpl implements IMemberPrivilegeService {
    @Autowired
    private IRoleDAO roleDAO ;
    @Autowired
    private IActionDAO actionDAO ;
    @Override
    public Map<String, Set<String>> getByMember(String mid) {
        Map<String,Set<String>> result = new HashMap<>() ;
        result.put("allRoles",this.roleDAO.findAllByMember(mid)) ;
        result.put("allActions",this.actionDAO.findAllByMember(mid)) ;
        return result;
    }
}
