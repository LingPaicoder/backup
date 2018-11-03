package ren.lingpai.lppoint.aspect;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ren.lingpai.lpagile.annotation.Aspect;
import ren.lingpai.lpagile.annotation.Controller;
import ren.lingpai.lpagile.part.ContextPart;
import ren.lingpai.lpagile.proxy.AspectProxy;

import java.lang.reflect.Method;

/**
 * 拦截Action所有方法
 * Created by lrp on 17-4-16.
 */
@Aspect(Controller.class)
public class ControllerAspect extends AspectProxy{

    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerAspect.class);

    private long begin;

    @Override
    public void before(Class<?> cls, Method method, Object[] params) throws Throwable {
        if(method.getName().contains("toString") || method.getName().contains("hashCode"))
            return;
        LOGGER.info("------[" + cls.getName() + ":" + method.getName() + " begin]------");
        begin = System.currentTimeMillis();
    }

    @Override
    public void after(Class<?> cls, Method method, Object[] params, Object result) throws Throwable {
        if(method.getName().contains("toString") || method.getName().contains("hashCode"))
            return;
        try{
            ContextPart.setRequestAttribute("currentUser",ContextPart.getSessionAttribute("currentUser"));
        }catch (Exception e){
            LOGGER.info("------[" + cls.getName() + ":" + method.getName() + " exception]------" + e.getMessage());
        }

        LOGGER.info("------[" + cls.getName() + ":" + method.getName() + " end]------"
        + (System.currentTimeMillis() - begin) + "ms");
    }
}
