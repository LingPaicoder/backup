package ren.lingpai.lpagile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ren.lingpai.lpagile.entity.Handler;
import ren.lingpai.lpagile.entity.LPData;
import ren.lingpai.lpagile.entity.LPView;
import ren.lingpai.lpagile.entity.RequestParam;
import ren.lingpai.lpagile.part.ActionMappingPart;
import ren.lingpai.lpagile.part.BeanContainerPart;
import ren.lingpai.lpagile.part.ConfigPart;
import ren.lingpai.lpagile.part.ContextPart;
import ren.lingpai.lpagile.part.RequestPart;
import ren.lingpai.lpagile.part.UploadPart;
import ren.lingpai.lpagile.part.ValueBindPart;
import ren.lingpai.lputil.json.JsonUtil;
import ren.lingpai.lputil.reflect.ReflectionUtil;
import ren.lingpai.lputil.string.StringUtil;

/**
 * 请求转发器
 * Created by lrp on 17-2-12.
 */
@WebServlet(urlPatterns = "/*",loadOnStartup = 0)
public class DispatcherServlet extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(DispatcherServlet.class);

    @Override
    public void init(ServletConfig servletConfig) throws ServletException{
        //初始化零件
        PartLoader.init();
        //获取ServletContext对象（用于注册Servlet）
        ServletContext servletContext = servletConfig.getServletContext();
        //注册处理JSP的Servlet
        ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");
        jspServlet.addMapping(ConfigPart.getAppJspPath() + "*");
        //注册处理静态资源的默认Servlet
        ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
        defaultServlet.addMapping(ConfigPart.getAppAssetPath() + "*");

        UploadPart.init(servletContext);
    }

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws
        ServletException,IOException{

        ContextPart.init(request,response);
        try {
            //获取请求方法与请求路径
            String requestMethod = request.getMethod().toLowerCase();
            String requestPath = request.getPathInfo();

            if(requestPath.equals("/favicon.ico")){
                return;
            }

            //获取Action处理器
            Handler handler = ActionMappingPart.getHandler(requestMethod,requestPath);
            //创建请求参数对象
            RequestParam param = null;

            if(null != handler){
                if(UploadPart.isMultipart(request)){
                    param = UploadPart.createParam(request);
                }else {
                    param = RequestPart.createParam(request);
                }
            }else {//404
                handler = ActionMappingPart.getHandler("get","/404");
            }

            //获取Controller类及其Bean实例
            Class<?> controllerClass = handler.getControllerClass();
            Object controllerBean = BeanContainerPart.getBean(controllerClass);
            //调用Action方法
            Object result = null;
            Method actionMethod = handler.getActionMethod();

            Object[] values = matchValues(handler,param);
            result = ReflectionUtil.invokeMethod(controllerBean,actionMethod,values);
            //处理Action方法返回值
            if(result instanceof LPView){
                //返回JSP页面
                handleViewResult((LPView) result,request,response);
            }else if(result instanceof LPData){
                //返回JSON数据
                handleDataResult((LPData) result,response);
            }
        }catch (Exception e){
            e.printStackTrace();
            LOGGER.info("------[DispatcherServlet:service]exception------",e);
        }
        finally {
            ContextPart.destroy();
        }
    }

    /**
     * 参数绑定
     * @return
     */
    private Object[] matchValues(Handler handler,RequestParam param) {
        if (handler.getParamTypes().isEmpty()) {
            return null;
        }
        Object[] params = new Object[handler.getParamTypes().size()];
        for (int i = 0 ; i < handler.getParamTypes().size() ; i ++) {
            String paramName = handler.getParamNames().get(i);
            Class<?> paramType = handler.getParamTypes().get(i);
            Object value = param.getFieldMap().get(paramName);
            //普通参数
            if(null != value){
                if (ValueBindPart.canBind(paramType)){
                    params[i] = ValueBindPart.bindBasic(paramType,value);
                }
            }
            if (ValueBindPart.canBind(paramType)) {
                continue;
            }
            //对象参数
            params[i] = ValueBindPart.bindObject(paramType,param);
        }
        return params;
    }

    /**
     * 返回JSP页面
     * @param view
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    private void handleViewResult(LPView view,HttpServletRequest request,HttpServletResponse response)
            throws IOException,ServletException{
        String path = view.getPath();
        if(StringUtil.isNotEmpty(path)){
            if(path.startsWith("/")){
                response.sendRedirect(request.getContextPath() + path);
            }else {
                Map<String,Object> model = view.getModel();
                for(Map.Entry<String,Object> entry:model.entrySet()){
                    request.setAttribute(entry.getKey(),entry.getValue());
                }
                request.getRequestDispatcher(ConfigPart.getAppJspPath() + path).forward(request,response);
            }
        }
    }

    /**
     * 返回JSON数据
     * @param data
     * @param response
     * @throws IOException
     */
    private void handleDataResult(LPData data,HttpServletResponse response) throws IOException{
        Object model = data.getModel();
        if(null != model){
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter writer = response.getWriter();
            String json = JsonUtil.toJson(model);
            writer.write(json);
            writer.flush();
            writer.close();
        }
    }

}
