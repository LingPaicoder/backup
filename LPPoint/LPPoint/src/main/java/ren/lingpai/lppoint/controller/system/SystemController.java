package ren.lingpai.lppoint.controller.system;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ren.lingpai.lpagile.annotation.*;
import ren.lingpai.lpagile.entity.LPData;
import ren.lingpai.lpagile.entity.LPView;
import ren.lingpai.lpagile.part.ContextPart;
import ren.lingpai.lpagile.plugin.security.SecurityPart;
import ren.lingpai.lppoint.entity.UserDO;
import ren.lingpai.lppoint.service.UserService;

/**
 * Created by lrp on 17-5-10.
 */
@Controller("")
public class SystemController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SystemController.class);

    @Inject
    private UserService userService;

    /**
     * 进入系统首页
     * @return
     */
    @Get
    @Action("/index")
    public LPView index(){
        System.out.println("------<index>------");
        ContextPart.sendRedirect("/visitor_index/index_view");
        return new LPView("");
    }

    /**
     * 进入登录页面
     * @param href 请求来源
     * @return
     */
    @Get
    @Action("/login_view")
    public LPView login(String href){
        return new LPView("system/login.jsp").addModel("href",href);
    }

    /**
     * 注册
     * @param userDO
     * @return
     */
    @Post
    @Action("/register")
    public LPData register(UserDO userDO){
        String result = "";
        String password = userDO.getPassword();
        if (null == userDO) return new LPData(result);
        userDO.setCreateTime(new java.sql.Date(new java.util.Date().getTime()));
        //注册
        int registerResult = -1;
        try {
            registerResult = userService.register(userDO);
            switch (registerResult){
                case -1:
                    result = "-1:失败";
                    break;
                case -2:
                    result = "-2:用户名已存在，请重试！";
                    break;
                case 1:
                    result = "1:成功";
                    break;
            }
        }catch (Exception e){
            LOGGER.error("------</register>exception------",e);
        }
        if (registerResult > 0){
            //登录
            try {
                SecurityPart.login(userDO.getUsername(),password);
                ContextPart.setSessionAttribute("currentUser",userService.getUserByUsername(userDO.getUsername()));
            }catch (Exception e){
                LOGGER.error("------</register>exception------",e);
            }
        }
        return new LPData(result);
    }

    /**
     * 登录
     * @param userDO
     * @param href
     * @return
     */
    @Post
    @Action("/login")
    public LPData login(String href , UserDO userDO){
        String result = "-1";
        if (null == userDO) return new LPData(result);
        try {
            SecurityPart.login(userDO.getUsername(),userDO.getPassword());
            ContextPart.setSessionAttribute("currentUser",userService.getUserByUsername(userDO.getUsername()));
            result = "1";
        }catch (Exception e){
            LOGGER.error("------</login>exception------",e);
            result = "-1";
        }
        if("1".equals(result)){
            if((href.length() > 0) && (0 == href.indexOf("/"))){
                result = result + ":" + href;
            }else{
                result = result + ":" + "/visitor_index/index_view";
            }
        }
        return new LPData(result);
    }

    /**
     * 进入404页面
     * @return
     */
    @Get
    @Action("/404")
    public LPView err404(){
        return new LPView("404.jsp");
    }

}
