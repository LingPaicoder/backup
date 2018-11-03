package ren.lingpai.lpagile.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.List;

/**
 * 代理工厂
 * 负责创建代理对象
 * 需要提供一个方法，输入一个目标类和一组Proxy接口实现，输出一个代理对象。
 * Created by lrp on 17-4-16.
 */
public class ProxyFactory {

    public static<T> T createProxy(final Class<?> targetClass,final List<Proxy> proxyList){
        return (T) Enhancer.create(targetClass, new MethodInterceptor() {
            @Override
            public Object intercept(Object targetObject, Method targetMethod, Object[] methodParams, MethodProxy methodProxy) throws Throwable {
                return new ProxyChain(targetClass,targetObject,targetMethod,methodProxy,methodParams,proxyList)
                        .doProxyChain();
            }
        });
    }

}
