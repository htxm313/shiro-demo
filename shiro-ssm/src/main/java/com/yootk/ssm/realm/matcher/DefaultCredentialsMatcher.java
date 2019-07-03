package com.yootk.ssm.realm.matcher;

import com.yootk.ssm.util.EncryptUtil;
import com.yootk.ssm.util.cache.RedisCache;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.cache.CacheManager;

import java.util.concurrent.atomic.AtomicInteger;

public class DefaultCredentialsMatcher extends SimpleCredentialsMatcher {

    private RedisCache<Object,Object> retryCache ;  //通过redis缓存管理器获取
    private String retryCacheName = "retryCache" ;
    private CacheManager cacheManager ; //定义缓存管理器
    private long expire = 50L ; //失效时间
    private int max = 5 ;   //登录尝试次数

    /**
     * 实现用户登陆次数的统计的处理操作，其中将以用户id作为key
     * @param mid   要保存在Redis数据库中的key数据
     */
    private void retry(String mid) {    //用户有可能会重复调用
        //每一次的计数都应该从前计数的基础之上处理
        AtomicInteger num = (AtomicInteger)this.retryCache.get(mid) ;
        if(num == null) {   //此时Redis并没有对本用户的计数信息进行存储
            num = new AtomicInteger(1) ;//准备一个新的计数
        }
        if(num.incrementAndGet() > this.max) {  //已经达到了最大尝试次数
            this.retryCache.putEx(mid,num,this.expire) ;    //设置了锁定操作
            throw new ExcessiveAttemptsException("用户登录次数过多，暂时锁定，请稍后再次尝试");
        }
        this.retryCache.put(mid,num) ;  //保存新的计数处理
    }

    /**
     * 如果登录完成之后应该进行统计计数的解锁
     * @param mid   Redis存储的key
     */
    public void unlock(String mid) {
        this.retryCache.remove(mid) ;
    }

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        //此处方法主要进行密码的匹配操作，这里面需要接收认证的Token信息
        //如果要进行密码次数的控制，必须首先获取用户的登陆id的信息
        String mid = (String) token.getPrincipal() ;    //获取用户名
        this.retry(mid) ;  //进行登录次数的统计

        //1、通过Token获取用户输入的密码内容
        Object tokenPassword = EncryptUtil.encode(super.toString(token.getCredentials())) ;
        //2、获取认证之后的密码内容
        Object infoPassword = super.getCredentials(info) ;
        boolean flag = super.equals(tokenPassword,infoPassword) ;
        if(flag) {  //用户登录成功
            this.unlock(mid); //成功则解锁
        }
        return flag ;
    }
    public void setRetryCacheName(String retryCacheName){   //设置尝试次数
        this.retryCacheName = retryCacheName ;
    }

    public void setCacheManager(CacheManager cacheManager) {
        this.cacheManager = cacheManager ;
        //RedisCache的功能要比Cache接口中定义的多
        this.retryCache = (RedisCache) this.cacheManager.getCache(this.retryCacheName) ;
    }

    public void setExpire(long expire) {
        this.expire = expire ;
    }
    public void setMax(int Max){
        this.max = max ;
    }
}
