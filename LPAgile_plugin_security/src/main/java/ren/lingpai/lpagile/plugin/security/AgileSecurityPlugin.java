package ren.lingpai.lpagile.plugin.security;

import org.apache.shiro.web.env.EnvironmentLoaderListener;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.Set;

/**
 * Agile Security插件
 * Created by lrp on 17-5-9.
 */
public class AgileSecurityPlugin implements ServletContainerInitializer {
    @Override
    public void onStartup(Set<Class<?>> set, ServletContext servletContext) throws ServletException {
        //设置初始化参数
        servletContext.setInitParameter("shiroConfigLocations","classpath:agile-security.ini");
        //注册Listener
        servletContext.addListener(EnvironmentLoaderListener.class);
        //注册Filter
        FilterRegistration.Dynamic agileSecurityFilter = servletContext.addFilter("AgileSecurityFilter",AgileSecurityFilter.class);
        agileSecurityFilter.addMappingForUrlPatterns(null,false,"/*");
    }
}
