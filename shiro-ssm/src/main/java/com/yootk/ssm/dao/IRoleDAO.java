package com.yootk.ssm.dao;

import java.util.Set;

public interface IRoleDAO {
    public Set<String> findAllByMember(String mid) ;
}
