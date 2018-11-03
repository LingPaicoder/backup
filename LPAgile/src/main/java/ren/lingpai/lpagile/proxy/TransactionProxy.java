package ren.lingpai.lpagile.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ren.lingpai.lpagile.annotation.Transaction;
import ren.lingpai.lpagile.part.DataBasePart;

import java.lang.reflect.Method;

/**
 * 事务代理
 * Created by lrp on 17-4-17.
 */
public class TransactionProxy implements Proxy {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionProxy.class);

    /**
     * 这个本地线程变量是一个标志
     * 可以保证同一线程中事务控制相关逻辑只会执行一次
     */
    private static final ThreadLocal<Boolean> FLAG_HOLDER = new ThreadLocal<Boolean>(){
        @Override
        protected Boolean initialValue() {
            return false;
        }
    };

    @Override
    public Object doProxy(ProxyChain proxyChain) throws Throwable {
        Object result;
        boolean flag = FLAG_HOLDER.get();
        Method method = proxyChain.getTargetMethod();
        if(!flag && method.isAnnotationPresent(Transaction.class)){
            FLAG_HOLDER.set(true);
            try{
                DataBasePart.beginTransaction();
                LOGGER.debug("------[method:" + method.getName() + "]begin transaction------");
                result = proxyChain.doProxyChain();
                DataBasePart.commitTransaction();
                LOGGER.debug("------[method:" + method.getName() + "]commit transaction------");
            } catch (Exception e){
                DataBasePart.rollbackTransaction();
                LOGGER.debug("------[method:" + method.getName() + "]rollback transaction------");
                throw e;
            }finally {
                FLAG_HOLDER.remove();
            }
        }else {
            result = proxyChain.doProxyChain();
        }
        return result;
    }

}
