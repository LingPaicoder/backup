package ren.lingpai.lpagile.plugin.security;

import java.util.Set;

/**
 * Agile Security接口
 * 可在应用中实现该接口，或者在agile.properties文件中提供以下基于SQL的配置项：
 * smart.security.jdbc.authc_query：根据用户名获取密码
 * smart.security.jdbc.roles_query：根据用户名获取角色名集合
 * smart.security.jdbc.permissions_query：根据角色名获取权限名集合
 * Created by lrp on 17-5-9.
 */
public interface AgileSecurity {

    /**
     * 根据用户名获取密码
     * @param username
     * @return
     */
    String getPassword(String username);

    /**
     * 根据用户名获取角色名集合
     * @param username
     * @return
     */
    Set<String> getRoleNameSet(String username);

    /**
     * 根据角色名获取权限名集合
     * @param roleName
     * @return
     */
    Set<String> getPermissionNameSet(String roleName);

}
