package ren.lingpai.lpagile;

import ren.lingpai.lpagile.part.*;
import ren.lingpai.lputil.clazz.ClassUtil;

/**
 * 加载Part类
 * Created by lrp on 17-2-12.
 */
public final class PartLoader {

    public static void init(){
        Class<?>[] classList = {
                ClassLoadPart.class,
                BeanContainerPart.class,
                //注意，AOPPart要在IOCPart之前加载
                //因为首先需要通过AopHelper获取代理对象，然后才能通过IOCPart进行依赖注入
                AOPPart.class,
                IOCPart.class,
                ActionMappingPart.class
        };
        for(Class<?> cls:classList){
            ClassUtil.loadClass(cls.getName());
        }
    }

}
