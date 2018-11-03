package ren.lingpai.lpagile.plugin.security.password;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import ren.lingpai.lputil.codec.CodecUtil;

/**
 * MD5密码匹配器
 * Created by lrp on 17-5-9.
 */
public class Md5CredentialsMatcher implements CredentialsMatcher {

    /**
     *
     * @param authenticationToken 可通过该参数获取从表单提交过来的密码，该密码是明文，尚未通过MD5加密
     * @param authenticationInfo 可通过该参数获取数据库中存储的密码，该密码已通过MD5加密
     * @return
     */
    @Override
    public boolean doCredentialsMatch(AuthenticationToken authenticationToken, AuthenticationInfo authenticationInfo) {
        //获取从表单提交过来的密码、明文，尚未通过MD5加密
        String submitted = String.valueOf(((UsernamePasswordToken)authenticationToken).getPassword());
        //获取数据库中存储的密码，已通过MD5加密
        String encrypted = String.valueOf(authenticationInfo.getCredentials());
        return CodecUtil.md5(submitted).equals(encrypted);
    }
}
