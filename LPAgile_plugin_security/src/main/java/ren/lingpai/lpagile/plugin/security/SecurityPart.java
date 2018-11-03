package ren.lingpai.lpagile.plugin.security;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ren.lingpai.lpagile.plugin.security.exception.AgileAuthcException;

/**
 * Security
 * Created by lrp on 17-5-10.
 */
public final class SecurityPart {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityPart.class);

    /**
     * 登录
     * @param username
     * @param password
     * @throws AgileAuthcException
     */
    public static void login(String username,String password) throws AgileAuthcException{
        Subject currentUser = SecurityUtils.getSubject();
        if(null != currentUser){
            UsernamePasswordToken token = new UsernamePasswordToken(username,password);
            try {
                currentUser.login(token);
            }catch (AuthenticationException e){
                LOGGER.error("login failure",e);
                throw new AgileAuthcException(e);
            }
        }
    }

    /**
     * 注销
     */
    public static void logout(){
        Subject currentUser = SecurityUtils.getSubject();
        if(null != currentUser){
            currentUser.logout();
        }
    }

}
