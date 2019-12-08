package com.yang.cache;

import com.yang.util.SerializeUtils;
import com.yang.util.SpringContextUtil;
import org.apache.ibatis.cache.Cache;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.locks.ReadWriteLock;


@Aspect
//@Configuration
public class MybatisCache implements Cache {

    private String id;

    public MybatisCache(String id) {
        System.out.println(id);
        this.id = id;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void putObject(Object key, Object value) {
        System.out.println("save" + key);
        StringRedisTemplate template = (StringRedisTemplate) SpringContextUtil.getBean(StringRedisTemplate.class);
        String serialize = SerializeUtils.serialize(value);
        template.opsForHash().put(id, key.toString(), serialize);


    }

    @Override
    public Object getObject(Object key) {
        System.out.println("get" + key);
        StringRedisTemplate template = (StringRedisTemplate) SpringContextUtil.getBean(StringRedisTemplate.class);
        String o = (String) template.opsForHash().get(id, key.toString());
        if (o == null) {
            return null;
        }
        Object o1 = SerializeUtils.serializeToObject(o);

        return o1;
    }

    @Override
    public Object removeObject(Object o) {
        return null;
    }

    @Override
    public void clear() {
        StringRedisTemplate template = (StringRedisTemplate) SpringContextUtil.getBean(StringRedisTemplate.class);
        template.delete(id);
    }

    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return null;
    }
}
