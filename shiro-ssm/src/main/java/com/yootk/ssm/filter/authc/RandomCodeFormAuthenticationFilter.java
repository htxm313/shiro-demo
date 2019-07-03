package com.yootk.ssm.filter.authc;

import com.yootk.ssm.filter.authc.exception.RandomCodeException;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

//追加一个新的过滤器类，进行验证码的处理
public class RandomCodeFormAuthenticationFilter extends FormAuthenticationFilter {
    private String randParamName = "read" ; //保存生成的session验证码的数据
    private String codeParamName = "code" ; //保存表单参数的名称

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpSession session = ((HttpServletRequest)request).getSession() ;  //获取session对象
        String rand = (String) session.getAttribute(this.randParamName) ;//获取生成的验证码数据
        String code = request.getParameter(this.codeParamName)  ;// 获取输入的验证码
        try{
            if(!(rand == null || "".equals(rand))){
                if(code == null || "".equals(code)){
                    throw new RandomCodeException("验证码不允许为空！") ;
                }else{
                    if(!rand.equalsIgnoreCase(code)) {  //验证码不正确
                        throw new RandomCodeException("验证码输入错误！") ;
                    }
                }
            }
        }catch (Exception e) {
            request.setAttribute(super.getFailureKeyAttribute(),e.getClass().getName());
            return true ;   //表示拒绝该用户的认证处理请求
        }
        return super.onAccessDenied(request, response); //调用用户认证
    }
    public void setRandParamName(String randParamName) {
        this.randParamName = randParamName ;
    }
    public void setCodeParamName(String codeParamName) {
        this.codeParamName = codeParamName ;
    }
}
