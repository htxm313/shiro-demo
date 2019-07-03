package com.yootk.ssm.util.cache.abs;


import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.util.SerializationUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;

public abstract class AbstractRedisCache<K,V> implements Cache<K,V> {
    //此时定义有一个公共的SpringDataRedis的连接工厂接口实例
    private RedisConnectionFactory redisConnectionFactory ;

    /**
     * 在Redis操作的时候所有的处理使用了泛型定义所以为了保证操作的公平性，所有数据都变为字节数组
     * 本方法是实现对象序列化处理的操作转换
     * @param obj   要转换的对象
     * @return  序列化之后的对象字节数组
     */
    protected byte[] objectToArray(Object obj) {    //实现对象转换为字节数组的操作
        return SerializationUtils.serialize(obj) ;

    }

    protected Object arraytoObject(byte [] data) {
        return SerializationUtils.deserialize(data) ;   //反序列化
    }

    @Override
    public V get(K k) throws CacheException {   //进行指定key的数据查询
        V resultObject = null ;
        RedisConnection connection = null ;
        try{
            connection = this.redisConnectionFactory.getConnection() ;
            resultObject = (V)this.arraytoObject(connection.get(this.objectToArray(k))) ;
        }catch (Exception e){

        }finally {
            if(connection != null) {
                connection.close();
            }
        }
        return resultObject;
    }

    @Override
    public V put(K k, V v) throws CacheException {  //实现数据存储
        RedisConnection connection = null ;
        try{
            connection = this.redisConnectionFactory.getConnection() ;
            connection.set(this.objectToArray(k),this.objectToArray(v)) ;
        }catch (Exception e){

        }finally {
            if(connection != null){
                connection.close();
            }
        }
        return v;
    }

    /**
     * 设置一个保存时间的数据存储
     * @param k 要保存的数据key
     * @param v 要保存数据的value
     * @param unit  处理的时间单元
     * @param expire    失效时间
     * @return  保存后的value数据
     * @throws CacheException   缓存异常
     */
    public V putEx(K k, V v , TimeUnit unit,long expire) throws CacheException{
        RedisConnection connection = null ;
        try{
            connection = this.redisConnectionFactory.getConnection() ;
            connection.setEx(this.objectToArray(k),TimeUnit.SECONDS.convert(expire,unit),this.objectToArray(v)) ;
        }catch (Exception e){

        }finally {
            if(connection != null){
                connection.close();
            }
        }
        return v ;
    }

    /**
     * 设置一个保存时间的数据存储
     * @param k 要保存的数据可以、
     * @param v 保存数据的value
     * @param expire    失效时间
     * @return  保存后的value数据
     * @throws CacheException   缓存异常
     */
    public V putEx(K k,V v,long expire) throws CacheException{
        return this.putEx(k,v,TimeUnit.SECONDS,expire) ;
    }

    /**
     * 设置一个保存时间的数据存储
     * @param k 要保存的数据可以、
     * @param v 保存数据的value
     * @param expire    失效时间
     * @return  保存后的value数据
     * @throws CacheException   缓存异常
     */
    public V putEx(K k,V v,String expire) throws CacheException{
        return this.putEx(k,v,TimeUnit.SECONDS,Long.parseLong(expire)) ;
    }

    @Override
    public V remove(K k) throws CacheException {
        V resultObject = null ;
        RedisConnection connection = null ;
        try{
            connection = this.redisConnectionFactory.getConnection() ;
            resultObject = (V)this.arraytoObject(connection.get(this.objectToArray(k))) ;
            connection.del(this.objectToArray(k)) ;
        }catch (Exception e){

        }finally {
            if(connection != null) {
                connection.close();
            }
        }
        return resultObject;
    }

    @Override
    public void clear() throws CacheException {
        RedisConnection connection = null ;
        try{
            connection = this.redisConnectionFactory.getConnection() ;
            connection.flushDb();   //清空所有的数据
        }catch (Exception e){

        }finally {
            if(connection != null) {
                connection.close();
            }
        }
    }

    @Override
    public int size() {
        return this.keys().size();
    }

    @Override
    public Set<K> keys() {
        Set<K> allKeys = new HashSet<>();
        RedisConnection connection = null ;
        try{
            connection = this.redisConnectionFactory.getConnection() ;
            Set<byte[]> keys = connection.keys(this.objectToArray("*")) ;   //查询全部的key
            for(byte [] key : keys) {
                allKeys.add((K)this.arraytoObject(key)) ;
            }
        }catch (Exception e){
        }finally {
            if(connection != null) {
                connection.close();
            }
        }
        return allKeys ;
    }

    @Override
    public Collection<V> values() {
        List<V> allValues = new ArrayList<>() ;
        RedisConnection connection = null ;
        try{
            connection = this.redisConnectionFactory.getConnection() ;
            Set<byte[]> keys = connection.keys(this.objectToArray("*")) ;   //查询全部的key
            for(byte[] key : keys) {
                allValues.add((V)this.arraytoObject(connection.get(key))) ;
            }
        }catch (Exception e){

        }finally {
            if(connection != null) {
                connection.close();
            }
        }
        return allValues;
    }

    /**
     * 通过外部传递一个RedisConnectionFactory接口实例
     * @param redisConnectionFactory    设置不为空的RedisConnectionFactory实例
     */
    public void setRedisConnectionFactory(RedisConnectionFactory redisConnectionFactory) {
        this.redisConnectionFactory = redisConnectionFactory ;
    }
}
