package ren.lingpai.lpagile.entity;

import ren.lingpai.lputil.clazz.ClassUtil;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 处理对象
 * Created by lrp on 17-2-9.
 */
public class Handler {

    /**
     * Controller类
     */
    private Class<?> controllerClass;

    /**
     * Action方法
     */
    private Method actionMethod;

    /**
     * 方法上所有参数类型，按顺序排列
     */
    private List<Class<?>> paramTypes;

    /**
     * 方法上所有参数名，按顺序排列
     */
    private List<String> paramNames;

    public Handler(Class<?> controllerClass,Method actionMethod){
        this.controllerClass = controllerClass;
        this.actionMethod = actionMethod;
        this.paramTypes = parseParamTypes();
        this.paramNames = parseParamNames();
    }

    public Method getActionMethod() {
        return actionMethod;
    }

    public Class<?> getControllerClass() {
        return controllerClass;
    }

    public List<Class<?>> getParamTypes() {
        return paramTypes;
    }

    public List<String> getParamNames() {
        return paramNames;
    }

    //优化：后期可在LPUtil中提供同一工具方法
    private List<Class<?>> parseParamTypes(){
        List<Class<?>> result = new ArrayList<Class<?>>();
        Class<?>[] paramTypes = actionMethod.getParameterTypes();
        for (Class typeClazz : paramTypes){
            result.add(typeClazz);
        }
        return result;
    }

    //优化：后期可在LPUtil中提供同一工具方法
    private List<String> parseParamNames(){
        List<String> result = new ArrayList<String>();
        String[] paramNames = ClassUtil.getMethodParamNames(this.controllerClass,this.actionMethod);
        for (String nameStr : paramNames){
            result.add(nameStr);
        }
        return result;
    }

    @Override
    public String toString() {
        return "Handler{" +
                "controllerClass=" + controllerClass.getName() +
                ", actionMethod=" + actionMethod.getName() +
                ", paramTypes=" + paramTypes +
                ", paramNames=" + paramNames +
                '}';
    }
}
