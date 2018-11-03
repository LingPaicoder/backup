package ren.lingpai.lpagile.plugin.security;

import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.mgt.CachingSecurityManager;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.apache.shiro.web.servlet.ShiroFilter;
import ren.lingpai.lpagile.plugin.security.realm.AgileCustomRealm;
import ren.lingpai.lpagile.plugin.security.realm.AgileJdbcRealm;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 安全过滤器
 * Created by lrp on 17-5-9.
 */
public class AgileSecurityFilter extends ShiroFilter {

    @Override
    public void init() throws Exception {
        super.init();
        WebSecurityManager webSecurityManager = super.getSecurityManager();
        //设置Realm（提供用户信息的数据源），可同时支持多个Realm，并按照先后顺序用逗号分割
        setRealms(webSecurityManager);
        //设置Cache，用于减少数据库查询次数，降低I/O访问
        setCache(webSecurityManager);
    }

    private void setRealms(WebSecurityManager webSecurityManager){
        //读取agile.plugin.security.realms配置项
        String securityRealms = SecurityConfig.getRealms();
        if(null != securityRealms){
            //根据逗号进行拆分
            String[] securityRealmArray = securityRealms.split(",");
            if(securityRealmArray.length > 0){
                //使Realm具备唯一性与顺序性
                Set<Realm> realms = new LinkedHashSet<Realm>();
                for (String securityRealm : securityRealmArray){
                    if(securityRealm.equalsIgnoreCase(SecurityConstant.REALMS_JDBC)){
                        //添加基于jdbc的Realm，需配置相关SQL查询语句
                        addJdbcRealm(realms);
                    }else if(securityRealm.equalsIgnoreCase(SecurityConstant.REALMS_CUSTOM)){
                        //添加基于定制化的Realm，需实现AgileSecurity接口
                        addCustomRealm(realms);
                    }
                }
                RealmSecurityManager realmSecurityManager = (RealmSecurityManager) webSecurityManager;
                realmSecurityManager.setRealms(realms);//设置Realm
            }
        }
    }

    private void addJdbcRealm(Set<Realm> realms){
        //添加自己实现的基于jdbc的Ream
        AgileJdbcRealm agileJdbcRealm = new AgileJdbcRealm();
        realms.add(agileJdbcRealm);
    }

    private void addCustomRealm(Set<Realm> realms){
        //读取agile.plugin.security.custom.class配置项
        AgileSecurity agileSecurity = SecurityConfig.getAgileSecurity();
        //添加自己实现的Realm
        AgileCustomRealm agileCustomRealm = new AgileCustomRealm(agileSecurity);
        realms.add(agileCustomRealm);
    }

    private void setCache(WebSecurityManager webSecurityManager){
        //读取agile.plugin.security.cache配置项
        if(SecurityConfig.isCache()){
            CachingSecurityManager cachingSecurityManager = (CachingSecurityManager) webSecurityManager;
            //使用基于内存的CacheManager
            CacheManager cacheManager = new MemoryConstrainedCacheManager();
            cachingSecurityManager.setCacheManager(cacheManager);
        }
    }
}
