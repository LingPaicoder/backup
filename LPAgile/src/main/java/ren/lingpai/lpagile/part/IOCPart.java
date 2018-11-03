package ren.lingpai.lpagile.part;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ren.lingpai.lpagile.annotation.Inject;
import ren.lingpai.lputil.collection.CollectionUtil;
import ren.lingpai.lputil.reflect.ReflectionUtil;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * 实现依赖注入。
 * 依赖注入，指的是成员变量的实例化，不需要开发者通过new操作来做，而是框架自身来实例化。
 *
 * 就是给干活的人安排好了具体的上下级关系。
 *
 * 【就像一个员工A知道自己手底下会有一个干m这种活的人，但是他不知道具体是谁，
   IOCHelper做的就是把这个干m活的活生生的员工B找到，然后带到A面前跟A说：就是他啦，你来指挥他就好了】
 *
 * 具体的实现是在static块中遍历BeanContainerPart中的Map，
   取出每一个class对象的成员变量，进而遍历成员变量，如果成员变量带有IOC标志注解，
   则会在class对象所对应的Object对象中注入成员变量的值（成员变量的值还是从BeanContainerPart的Map中取）。
 * Created by lrp on 17-2-9.
 */
public final class IOCPart {

    private static final Logger LOGGER = LoggerFactory.getLogger(IOCPart.class);

    static {
        Map<Class<?>,Object> beanMap = BeanContainerPart.getBeanMap();
        if(CollectionUtil.isNotEmpty(beanMap)){
            for(Map.Entry<Class<?>,Object> beanEntry : beanMap.entrySet()){
                Class<?> beanClass = beanEntry.getKey();
                Object beanInstance = beanEntry.getValue();
                Field[] beanFields = beanClass.getDeclaredFields();
                if(!CollectionUtil.isEmpty(beanFields)){
                    for (Field beanField : beanFields){
                        if(beanField.isAnnotationPresent(Inject.class)){
                            Class<?> beanFieldClass = beanField.getType();
                            Object beanFieldInstance = beanMap.get(beanFieldClass);
                            if(null != beanFieldInstance){
                                ReflectionUtil.setField(beanInstance,beanField,beanFieldInstance);
                            }
                        }
                    }
                }
            }
        }
        LOGGER.info("------[IOC完成：输出BEAN_MAP-IOC之后]------:" + beanMap.toString());
    }

}
