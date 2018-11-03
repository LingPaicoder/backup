package ren.lingpai.lpagile.part;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ren.lingpai.lpagile.annotation.Action;
import ren.lingpai.lpagile.annotation.Controller;
import ren.lingpai.lpagile.annotation.Get;
import ren.lingpai.lpagile.annotation.Post;
import ren.lingpai.lpagile.entity.Handler;
import ren.lingpai.lpagile.entity.Request;
import ren.lingpai.lputil.collection.CollectionUtil;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 给Action制造花名册。
 * 与BeanContainerPart类似，它也是实现一个花名册的功能，
   不过它的花名册不是给框架自身点名用的，而是给客户端点名用的。
 *
 * 具体的实现是先获取所有定义了Controller注解的类，再通过反射获取该类中带有Action注解的方法，
   然后获取Action注解中的“请求表达式”。接下来根据“请求表达式”获取‘请求方法’和‘请求路径’，
   封装一个【请求对象】。并和【处理对象】之间建立一个映射关系，放入Map中。
   同时提供一个可以根据【请求对象】获取【处理对象】的方法。
 *
 * Created by lrp on 17-2-9.
 */
public final class ActionMappingPart {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActionMappingPart.class);

    private static final Map<Request,Handler> ACTION_MAP = new HashMap<Request, Handler>();

    static {
        Set<Class<?>> controllerClassSet = ClassLoadPart.getControllerClassSet();
        if(CollectionUtil.isNotEmpty(controllerClassSet)){
            for(Class<?> controllerClass:controllerClassSet){
                String controllerPath = "";
                Controller controller = controllerClass.getAnnotation(Controller.class);
                if(null != controller){
                    String controllerMapping = controller.value();
                    if(controllerMapping.matches("/\\w*")){
                        controllerPath = controllerMapping;
                    }
                }
                Method[] methods = controllerClass.getDeclaredMethods();
                if(CollectionUtil.isNotEmpty(methods)){
                    for(Method method:methods){
                        //判断是否有Get注解
                        boolean getFlag = false;
                        if (method.isAnnotationPresent(Get.class)){
                            getFlag = true;
                        }
                        //判断是否有Post注解
                        boolean postFlag = false;
                        if (method.isAnnotationPresent(Post.class)){
                            postFlag = true;
                        }
                        //获得Action的Path
                        String actionPath = "";
                        if(method.isAnnotationPresent(Action.class)){
                            Action action = method.getAnnotation(Action.class);
                            String actionMapping = action.value();
                            if(actionMapping.matches("/\\w*")){
                                actionPath = actionMapping;
                            }
                        }
                        //有get注解
                        if (getFlag){
                            Request request = new Request("get",controllerPath + actionPath);
                            Handler handler = new Handler(controllerClass,method);
                            ACTION_MAP.put(request,handler);
                        }
                        //有post注解
                        if (postFlag){
                            Request request = new Request("post",controllerPath + actionPath);
                            Handler handler = new Handler(controllerClass,method);
                            ACTION_MAP.put(request,handler);
                        }
                        //get注解、post注解 全都没有
                        if (!getFlag && !postFlag){
                            Handler handler = new Handler(controllerClass,method);
                            ACTION_MAP.put(new Request("get",controllerPath + actionPath),handler);
                            ACTION_MAP.put(new Request("post",controllerPath + actionPath),handler);
                        }
                    }
                }
            }
        }
        LOGGER.info("------[构建路由完成：输出ACTION_MAP]------：" + ACTION_MAP.toString());
    }

    /**
     * 获取Handler
     * @param requestMethod
     * @param requestPath
     * @return
     */
    public static Handler getHandler(String requestMethod,String requestPath){
        Request request = new Request(requestMethod,requestPath);
        return ACTION_MAP.get(request);
    }

}
