package ren.lingpai.lpagiledemo.aspect;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ren.lingpai.lpagile.annotation.Aspect;
import ren.lingpai.lpagile.annotation.Controller;
import ren.lingpai.lpagile.proxy.AspectProxy;

import java.lang.reflect.Method;

/**
 * 拦截Action所有方法
 * Created by lrp on 17-4-16.
 */
//Controller：目标类
//Aspect注解加到AspectProxy实现类上
//这个注解是连接目标类与代理类的【桥梁】
@Aspect(Controller.class)
public class ControllerAspect extends AspectProxy{

    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerAspect.class);

    private long begin;

    @Override
    public void before(Class<?> cls, Method method, Object[] params) throws Throwable {
        LOGGER.debug("------[begin]------");
        LOGGER.debug(String.format("------[class:%s]------",cls.getName()));
        LOGGER.debug(String.format("------[method:%s]------",method.getName()));
        LOGGER.debug("------[begin end]------");
        begin = System.currentTimeMillis();
    }

    @Override
    public void after(Class<?> cls, Method method, Object[] params, Object result) throws Throwable {
        LOGGER.debug(String.format("------[after]------"));
        LOGGER.debug(String.format("------[time:%dms]------",System.currentTimeMillis() - begin));
        LOGGER.debug("------[after end]------");
    }
}
