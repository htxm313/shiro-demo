package com.yootk.ssm.service.impl;

import com.yootk.ssm.dao.IMemberDAO;
import com.yootk.ssm.service.IMemberService;
import com.yootk.ssm.vo.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements IMemberService {

    @Autowired
    private IMemberDAO memberDAO ;
    @Override
    public Member get(String mid) {
        return this.memberDAO.findById(mid);
    }
}
