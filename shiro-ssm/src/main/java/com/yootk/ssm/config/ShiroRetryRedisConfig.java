package com.yootk.ssm.config;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@PropertySource("classpath:config/redis.properties")
public class ShiroRetryRedisConfig {
    @Bean("shiroRetryRedisConfiguration")
    public RedisStandaloneConfiguration getRedisConfiguration(
            @Value("${shiro.retry.redis.host}") String hostName ,
            @Value("${shiro.retry.redis.port}") int port,
            @Value("${shiro.retry.redis.auth}") String password,
            @Value("${shiro.retry.redis.database}") int database
    ) {
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration() ;
        configuration.setHostName(hostName); // 设置Redis主机名称
        configuration.setPort(port); // 设置Redis的访问端口
        configuration.setPassword(RedisPassword.of(password)); // 设置密码
        configuration.setDatabase(database); // 设置数据库索引
        return configuration ;
    }
    @Bean("shiroRetryObjectPoolConfig")
    public GenericObjectPoolConfig getObjectPoolConfig(
            @Value("${shiro.retry.redis.pool.maxTotal}") int maxTotal ,
            @Value("${shiro.retry.redis.pool.maxIdle}") int maxIdle ,
            @Value("${shiro.retry.redis.pool.minIdle}") int minIdle ,
            @Value("${shiro.retry.redis.pool.testOnBorrow}") boolean testOnBorrow
    ) {
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig() ;
        poolConfig.setMaxTotal(maxTotal);
        poolConfig.setMaxIdle(maxIdle);
        poolConfig.setMinIdle(minIdle);
        poolConfig.setTestOnBorrow(testOnBorrow);
        return poolConfig ;
    }
    @Bean("shiroRetryClientConfiguration")
    public LettuceClientConfiguration getLettuceClientConfiguration(
            @Autowired GenericObjectPoolConfig shiroRetryObjectPoolConfig
    ) { // 创建Lettuce组件的连接池客户端配置对象
        return LettucePoolingClientConfiguration.builder().poolConfig(shiroRetryObjectPoolConfig).build() ;
    }
    @Bean("shiroRetryRedisConnectionFactory")
    public RedisConnectionFactory getConnectionFactory(
            @Autowired RedisStandaloneConfiguration shiroRetryRedisConfiguration ,
            @Autowired LettuceClientConfiguration shiroRetryClientConfiguration
    ) {
        LettuceConnectionFactory connectionFactory = new LettuceConnectionFactory(shiroRetryRedisConfiguration,shiroRetryClientConfiguration) ;
        return connectionFactory ;
    }
    @Bean("shiroRetryRedisTemplate")
    public RedisTemplate getRedisTempalate(
            @Autowired RedisConnectionFactory shiroRetryRedisConnectionFactory
    ) {
        RedisTemplate<Object,Object> redisTemplate = new RedisTemplate<>() ;
        redisTemplate.setConnectionFactory(shiroRetryRedisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer()); // 数据的key通过字符串存储
        redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer()); // 保存的value为对象
        redisTemplate.setHashKeySerializer(new StringRedisSerializer()); // 数据的key通过字符串存储
        redisTemplate.setHashValueSerializer(new JdkSerializationRedisSerializer()); // 保存的value为对象
        return redisTemplate ;
    }
}
