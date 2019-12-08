package com.yang.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Aspect
@Configuration
public class CacheAspect {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Around("@annotation(com.yang.annotaction.CacheAnnotaction)")
    public Object addCache(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        //通过反射获取类名
        String serviceName = proceedingJoinPoint.getSignature().getDeclaringTypeName();
        //通过反射获取方法名
        String name = proceedingJoinPoint.getSignature().getName();
        //通过反射获取方法的参数
        Object[] args = proceedingJoinPoint.getArgs();

        //拼接方法和类名
        StringBuilder methodName = new StringBuilder(name);
        for (int i = 0; i < args.length; i++) {
            methodName.append("-" + args[i]);
        }
        //对key的序列化
        stringRedisTemplate.setKeySerializer(new StringRedisSerializer());
        //对hash的序列化
        stringRedisTemplate.setHashKeySerializer(new StringRedisSerializer());
        //序列化值
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        //为了反序列化的成功
        ObjectMapper objectMapper = new ObjectMapper();
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        //对值的序列化
        stringRedisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        Boolean aBoolean = stringRedisTemplate.opsForHash().hasKey(serviceName, methodName.toString());
        if (aBoolean) {
            //返回缓存
            return stringRedisTemplate.opsForHash().get(serviceName, methodName.toString());
        }
        //service层方法的返回值
        Object proceed = proceedingJoinPoint.proceed();
        //添加缓存
        stringRedisTemplate.opsForHash().put(serviceName, methodName.toString(), proceed);
        return proceed;

    }

    @Around("@annotation(com.yang.annotaction.CacheRemoveAnnotaction)")
    public Object removeCache(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object proceed = proceedingJoinPoint.proceed();

        //获取类名
        String className = proceedingJoinPoint.getSignature().getDeclaringTypeName();
        //判断key是否 存在
        Boolean aBoolean = stringRedisTemplate.hasKey(className);
        if (aBoolean) {
            stringRedisTemplate.opsForHash().delete(className);
        }
        return proceed;

    }
}
