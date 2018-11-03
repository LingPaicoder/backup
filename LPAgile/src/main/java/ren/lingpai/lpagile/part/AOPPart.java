package ren.lingpai.lpagile.part;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ren.lingpai.lpagile.annotation.Aspect;
import ren.lingpai.lpagile.annotation.Service;
import ren.lingpai.lpagile.proxy.AspectProxy;
import ren.lingpai.lpagile.proxy.Proxy;
import ren.lingpai.lpagile.proxy.ProxyFactory;
import ren.lingpai.lpagile.proxy.TransactionProxy;

import java.lang.annotation.Annotation;
import java.util.*;

/**
 * 实现切面。
 * Created by lrp on 17-4-16.
 */
public  class AOPPart {

    private static final Logger LOGGER = LoggerFactory.getLogger(AOPPart.class);

    static {
        try {
            //获得【代理类】和【目标类集合】的映射关系proxyMap
            Map<Class<?>,Set<Class<?>>> proxyMap = getProxyMap();
            //根据proxyMap得到【目标类】和【代理对象列表】的映射关系targetMap
            Map<Class<?>,List<Proxy>> targetMap = getTargetMap(proxyMap);
            //遍历targetMap，生产目标类的代理对象，注入到Bean容器中
            for(Map.Entry<Class<?>,List<Proxy>> targetEntry : targetMap.entrySet()){
                Class<?> targetClass = targetEntry.getKey();
                List<Proxy> proxyList = targetEntry.getValue();
                Object proxy = ProxyFactory.createProxy(targetClass,proxyList);
                BeanContainerPart.setBean(targetClass,proxy);
            }
            LOGGER.info("------[AOP完成]------：");
        }catch (Exception e){
            LOGGER.error("------[aop failure]------",e);
        }
    }

    /**
     * 获得【代理类】和【目标类集合】的映射关系proxyMap
     * @return
     * @throws Exception
     */
    private static Map<Class<?>,Set<Class<?>>> getProxyMap() throws Exception{
        Map<Class<?>,Set<Class<?>>> proxyMap = new HashMap<Class<?>, Set<Class<?>>>();
        //填充切面代理
        addAspectProxy(proxyMap);
        //填充事务代理
        addTransactionProxy(proxyMap);
        return proxyMap;
    }

    /**
     * 根据proxyMap得到【目标类】和【代理对象列表】的映射关系targetMap
     * @param proxyMap
     * @return
     * @throws Exception
     */
    private static Map<Class<?>,List<Proxy>> getTargetMap(Map<Class<?>,Set<Class<?>>> proxyMap) throws Exception{
        Map<Class<?>,List<Proxy>> targetMap = new HashMap<Class<?>, List<Proxy>>();
        for(Map.Entry<Class<?>,Set<Class<?>>> proxyEntry : proxyMap.entrySet()){
            Class<?> proxyClass = proxyEntry.getKey();
            Set<Class<?>> targetClassSet = proxyEntry.getValue();
            for(Class<?> targetClass : targetClassSet){
                Proxy proxy = (Proxy)proxyClass.newInstance();
                if(targetMap.containsKey(targetClass)){
                    targetMap.get(targetClass).add(proxy);
                }else {
                    List<Proxy> proxyList = new ArrayList<Proxy>();
                    proxyList.add(proxy);
                    targetMap.put(targetClass,proxyList);
                }
            }
        }
        return targetMap;
    }

    /**
     * 向proxyMap中填充切面代理
     * 必须同时满足以下两个要求：
     * 1.扩展AspectProxy抽象类
     * 2.带有Aspect注解
     * @param proxyMap
     * @throws Exception
     */
    private static void addAspectProxy(Map<Class<?>,Set<Class<?>>> proxyMap) throws Exception{
        Set<Class<?>> proxyClassSet = ClassLoadPart.getClassSetBySuper(AspectProxy.class);
        for(Class<?> proxyClass:proxyClassSet){
            if(proxyClass.isAnnotationPresent(Aspect.class)){
                Aspect aspect = proxyClass.getAnnotation(Aspect.class);
                //获取目标类集合
                Set<Class<?>> targetClassSet = new HashSet<Class<?>>();
                Class<? extends Annotation> annotation = aspect.value();
                if(null != annotation && !annotation.equals(Aspect.class)){
                    targetClassSet.addAll(ClassLoadPart.getClassSetByAnnotation(annotation));
                }
                //将代理类和目标类集合的映射存入set
                proxyMap.put(proxyClass,targetClassSet);
            }
        }
    }

    /**
     * 向proxyMap中填充事务代理
     * @param proxyMap
     */
    private static void addTransactionProxy(Map<Class<?>,Set<Class<?>>> proxyMap){
        Set<Class<?>> serviceClassSet = ClassLoadPart.getClassSetByAnnotation(Service.class);
        proxyMap.put(TransactionProxy.class,serviceClassSet);
    }

}
