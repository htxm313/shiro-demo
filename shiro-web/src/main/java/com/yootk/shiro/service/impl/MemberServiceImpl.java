package com.yootk.shiro.service.impl;

import com.yootk.shiro.service.IMemberService;
import com.yootk.shiro.util.DatabaseConnection;
import com.yootk.shiro.vo.Member;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MemberServiceImpl implements IMemberService {
    @Override
    public Member get(String mid) {
        Member member = null ;
        try{
            String sql = "SELECT mid,name,password,locked FROM member WHERE mid=?" ;
            PreparedStatement pstmt = DatabaseConnection.getConnection().prepareStatement(sql) ;
            pstmt.setString(1,mid);
            ResultSet rs = pstmt.executeQuery() ;
            if(rs.next()){
                member = new Member() ;
                member.setMid(rs.getString(1));
                member.setName(rs.getString(2));
                member.setPassword(rs.getString(3));
                member.setLocked(rs.getInt(4));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DatabaseConnection.close();
        }
        return member ;
    }

    @Override
    public Map<String, Set<String>> findPrivilegeByMember(String mid) {
        Map<String,Set<String>> result = new HashMap<>() ;
        try{
            String sql = "SELECT rid FROM member_role WHERE mid=?" ;
            PreparedStatement pstmt = DatabaseConnection.getConnection().prepareStatement(sql) ;
            pstmt.setString(1,mid);
            ResultSet rs = pstmt.executeQuery() ;
            Set<String> roleSet = new HashSet<>() ;
            while (rs.next()) {
                roleSet.add(rs.getString(1)) ;
            }
            result.put("allRoles",roleSet) ;    // 保存获取的角色信息
            sql = "SELECT actid FROM action WHERE rid IN (" +
                    "   SELECT rid FROM member_role WHERE mid=?)" ;
            pstmt = DatabaseConnection.getConnection().prepareStatement(sql) ;
            pstmt.setString(1,mid);
            rs = pstmt.executeQuery() ;
            Set<String> actionSet = new HashSet<>() ;
            while (rs.next()) {
                actionSet.add(rs.getString(1)) ;
            }
            result.put("allActions",actionSet) ;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.close();
        }
        return result ;

    }
}
