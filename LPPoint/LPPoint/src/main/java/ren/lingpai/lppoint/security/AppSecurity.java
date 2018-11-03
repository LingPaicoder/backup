package ren.lingpai.lppoint.security;

import ren.lingpai.lpagile.part.DataBasePart;
import ren.lingpai.lpagile.plugin.security.AgileSecurity;

import java.util.Set;

/**
 * 应用安全控制
 * Created by lrp on 17-5-10.
 */
public class AppSecurity implements AgileSecurity {

    @Override
    public String getPassword(String username) {
        String sql = "SELECT m_password FROM t_user WHERE m_username = ?";
        return DataBasePart.query(sql,username);
    }

    @Override
    public Set<String> getRoleNameSet(String username) {
        String sql = "SELECT r.m_rolename FROM t_user u, t_user_role ur, t_role r WHERE u.m_id = ur.m_user_id AND r.m_id = ur.m_role_id AND u.m_username = ?";
        return DataBasePart.query(sql,username);
    }

    @Override
    public Set<String> getPermissionNameSet(String roleName) {
        String sql = "SELECT p.m_permission_name FROM t_role r, t_role_permission rp, t_permission p WHERE r.m_id = rp.m_role_id AND p.m_id = rp.m_permission_id AND r.m_role_name = ?";
        return DataBasePart.query(sql,roleName);
    }
}
