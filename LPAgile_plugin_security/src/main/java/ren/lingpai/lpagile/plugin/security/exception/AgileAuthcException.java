package ren.lingpai.lpagile.plugin.security.exception;

/**
 * 认证异常（当非法访问时抛出）
 * Created by lrp on 17-5-9.
 */
public class AgileAuthcException extends Exception{

    public AgileAuthcException(){
        super();
    }

    public AgileAuthcException(String message){
        super(message);
    }

    public AgileAuthcException(String message,Throwable cause){
        super(message,cause);
    }

    public AgileAuthcException(Throwable cause){
        super(cause);
    }

}
