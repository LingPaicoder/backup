package ren.lingpai.lpagile.plugin.security.realm;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import ren.lingpai.lpagile.plugin.security.AgileSecurity;
import ren.lingpai.lpagile.plugin.security.SecurityConstant;
import ren.lingpai.lpagile.plugin.security.password.Md5CredentialsMatcher;
import ren.lingpai.lputil.collection.CollectionUtil;

import java.util.HashSet;
import java.util.Set;

/**
 * 基于Agile的自定义Realm（需要实现SmartSecurity接口）
 * Created by lrp on 17-5-9.
 */
public class AgileCustomRealm extends AuthorizingRealm{

    private final AgileSecurity agileSecurity;

    public AgileCustomRealm(AgileSecurity agileSecurity){
        this.agileSecurity = agileSecurity;
        super.setName(SecurityConstant.REALMS_CUSTOM);
        super.setCredentialsMatcher(new Md5CredentialsMatcher());//使用MD5加密算法
    }

    /**
     * Authentication认证信息
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        if(null == authenticationToken){
            throw new AuthenticationException("parameter token is null");
        }
        //通过AuthenticationToken对象获取从表单中提交过来的用户名
        String username = ((UsernamePasswordToken) authenticationToken).getUsername();

        //通过AgileSecurity接口并根据用户名获取数据库中存放的密码
        String password = agileSecurity.getPassword(username);

        //将用户名与密码放入AuthenticationInfo对象中，便于后续的认证操作
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo();
        authenticationInfo.setPrincipals(new SimplePrincipalCollection(username,super.getName()));
        authenticationInfo.setCredentials(password);
        return authenticationInfo;
    }

    /**
     * Authorization授权信息
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        if(null != principalCollection){
            throw new AuthorizationException("parameter principals is null");
        }
        //获取已认证用户的用户名
        String username = (String) super.getAvailablePrincipal(principalCollection);

        //通过AgileSecurity接口并根据用户名获取角色名集合
        Set<String> roleNameSet = agileSecurity.getRoleNameSet(username);

        //通过AgileSecurity接口并根据角色名获取与其对应的权限名集合
        Set<String> permissionNameSet = new HashSet<String>();
        if(CollectionUtil.isNotEmpty(roleNameSet)){
            for (String roleName : roleNameSet){
                Set<String> currentPermissionNameSet = agileSecurity.getPermissionNameSet(roleName);
                permissionNameSet.addAll(currentPermissionNameSet);
            }
        }

        //将角色名集合与权限名集合放入AuthorizationInfo对象中，便于后续的授权操作
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.setRoles(roleNameSet);
        authorizationInfo.setStringPermissions(permissionNameSet);
        return authorizationInfo;
    }
}
