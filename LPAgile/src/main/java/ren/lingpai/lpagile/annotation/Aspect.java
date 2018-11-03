package ren.lingpai.lpagile.annotation;

import java.lang.annotation.*;

/**
 * 切面注解
 * Created by lrp on 17-4-16.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect {

    Class<? extends Annotation> value();

}
