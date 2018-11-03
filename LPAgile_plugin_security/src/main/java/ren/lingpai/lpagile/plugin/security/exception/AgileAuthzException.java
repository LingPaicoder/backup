package ren.lingpai.lpagile.plugin.security.exception;

/**
 * 授权异常（当权限无效时抛出）
 * Created by lrp on 17-5-9.
 */
public class AgileAuthzException extends RuntimeException {

    public AgileAuthzException(){
        super();
    }

    public AgileAuthzException(String message){
        super(message);
    }

    public AgileAuthzException(String message, Throwable cause){
        super(message,cause);
    }

    public AgileAuthzException(Throwable cause){
        super(cause);
    }

}
