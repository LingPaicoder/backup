package ren.lingpai.lpagile.proxy;

/**
 * 代理接口
 * Created by lrp on 17-4-16.
 */
public interface Proxy {

    /**
     * 执行链式代理
     * @param proxyChain
     * @return
     * @throws Throwable
     */
    Object doProxy(ProxyChain proxyChain) throws Throwable;

}
