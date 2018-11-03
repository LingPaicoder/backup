package ren.lingpai.lpagile.part;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ren.lingpai.lputil.reflect.ReflectionUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Bean容器。
 * 所谓的容器，就是真正拿到干活的人（实实在在的对象），
   并且能方便的找到干活的人（将对象放到一个Map中，从而可以根据相关信息，也就是Class对象，找到Object对象）。
 *
 * 【就像所有员工聚到一起来了，还能根据花名册点名】
 * Created by lrp on 17-2-8.
 */
public final class BeanContainerPart {

    private static final Logger LOGGER = LoggerFactory.getLogger(BeanContainerPart.class);

    private static final Map<Class<?>,Object> BEAN_MAP = new HashMap<Class<?>, Object>();

    static {
        Set<Class<?>> beanClassSet = ClassLoadPart.getBeanClassSet();
        for(Class<?> beanClass : beanClassSet){
            Object obj = ReflectionUtil.newInstance(beanClass);
            BEAN_MAP.put(beanClass,obj);
        }
        LOGGER.info("------[Bean容器初始化完成：输出BEAN_MAP]------:" + BEAN_MAP.toString());
    }

    /**
     * 获取Bean映射
     * @return
     */
    public static Map<Class<?>,Object> getBeanMap(){
        return BEAN_MAP;
    }

    /**
     * 获取Bean实例
     * @param cls
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(Class<T> cls){
        if(!BEAN_MAP.containsKey(cls)){
            throw new RuntimeException("can not get bean by class:" + cls);
        }
        return (T)BEAN_MAP.get(cls);
    }

    /**
     * 设置Bean实例
     * @param cls
     * @param obj
     */
    public static void setBean(Class<?> cls,Object obj){
        BEAN_MAP.put(cls,obj);
    }

}
