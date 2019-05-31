package cn.meteoroid.common.extend.lock;

import cn.meteoroid.common.support.Exceptions;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 防止重复提交注解拦截器
 * @author Kelvin Song
 * @date 2019-05-31 16:45
 */
@Aspect
@Configuration
public class RepeatLockInterceptor {

    /**
     * 最大缓存 500 个
     * 设置写缓存后 5 秒钟过期
     */
    private static final Cache<String, Object> CACHES = CacheBuilder.newBuilder().maximumSize(500).expireAfterWrite(5, TimeUnit.SECONDS).build();

    @Around("@within(cn.meteoroid.common.extend.lock.RepeatLock)")
    public Object interceptor(ProceedingJoinPoint point) {
        Class<?> type = point.getTarget().getClass();
        RepeatLock repeatLock = type.getAnnotation(RepeatLock.class);

        String value = repeatLock.value();
        if (StringUtils.isEmpty(value)) {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            value = request.getMethod() + ":" + request.getRequestURI() + ":" + type.getSimpleName();
        }

        String key = UUID.nameUUIDFromBytes(value.getBytes()).toString();

        if (CACHES.getIfPresent(key) != null) {
            throw new Exceptions.RepeatSubmitException("请勿重复请求");
        }
        //第一次请求,将key压入缓存中
        CACHES.put(key, key);

        try {
            return point.proceed();
        } catch (Throwable throwable) {
            throw new RuntimeException("服务器异常");
        }
    }
}
