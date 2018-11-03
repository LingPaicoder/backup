package ren.lingpai.lpagile.part;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 上下文
 * Created by lrp on 17-4-22.
 */
public final class ContextPart {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContextPart.class);

    private static final ThreadLocal<ContextPart> CONTEXT_THREAD_LOCAL = new ThreadLocal<ContextPart>();

    private HttpServletRequest request;
    private HttpServletResponse response;

    private ContextPart(HttpServletRequest request,HttpServletResponse response){
        this.request = request;
        this.response = response;
    }

    /**
     * 初始化
     * @param request
     * @param response
     */
    public static void init(HttpServletRequest request,HttpServletResponse response){
        CONTEXT_THREAD_LOCAL.set(new ContextPart(request,response));
    }

    /**
     * 销毁
     */
    public static void destroy(){
        CONTEXT_THREAD_LOCAL.remove();
    }

    /**
     * 获取Request对象
     * @return
     */
    private static HttpServletRequest getRequest(){
        return CONTEXT_THREAD_LOCAL.get().request;
    }

    /**
     * 获取Response对象
     * @return
     */
    private static HttpServletResponse getResponse(){
        return CONTEXT_THREAD_LOCAL.get().response;
    }

    /**
     * 获取Session对象
     * @return
     */
    private static HttpSession getSession(){
        return getRequest().getSession();
    }

    /**
     * 获取ServletContext对象
     * @return
     */
    private static ServletContext getServletContext(){
        return getRequest().getServletContext();
    }

    /**
     * 将属性放入Request中
     * @param key
     * @param value
     */
    public static void setRequestAttribute(String key,Object value){
        getRequest().setAttribute(key,value);
    }

    /**
     * 从Request中获取属性
     * @param key
     * @param <T>
     * @return
     */
    public static <T> T getRequestAttribute(String key){
        return (T) getRequest().getAttribute(key);
    }

    /**
     * 从Request中移除属性
     * @param key
     */
    public static void remoteRequestAttribute(String key){
        getRequest().removeAttribute(key);
    }

    /**
     * 发送重定向响应
     * @param location
     */
    public static void sendRedirect(String location){
        try {
            getResponse().sendRedirect(getRequest().getContextPath() + location);
        }catch (IOException e){
            LOGGER.error("------[ContextPart:sendRedirect]exception------",e);
        }
    }

    /**
     * 将属性放入Session中
     * @param key
     * @param value
     */
    public static void setSessionAttribute(String key,Object value){
        getSession().setAttribute(key,value);
    }

    /**
     * 从Session中获取属性
     * @param key
     * @param <T>
     * @return
     */
    public static <T> T getSessionAttribute(String key){
        return (T) getRequest().getSession().getAttribute(key);
    }

    /**
     * 从Session中移除属性
     * @param key
     */
    public static void removeSessionAttribute(String key){
        getRequest().getSession().removeAttribute(key);
    }

    /**
     * 使Session失效
     */
    public static void invalidateSession(){
        getRequest().getSession().invalidate();
    }

}
