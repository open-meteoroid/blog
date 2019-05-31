package cn.meteoroid.common.extend.lock;

import java.lang.annotation.*;

/**
 * 防止重复提交锁
 * @author Kelvin Song
 * @date 2019-05-31 16:43
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface RepeatLock {

    String value() default "";

}
