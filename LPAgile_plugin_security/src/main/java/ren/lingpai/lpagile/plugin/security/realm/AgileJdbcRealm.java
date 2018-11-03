package ren.lingpai.lpagile.plugin.security.realm;

import org.apache.shiro.realm.jdbc.JdbcRealm;
import ren.lingpai.lpagile.part.DataBasePart;
import ren.lingpai.lpagile.plugin.security.SecurityConfig;
import ren.lingpai.lpagile.plugin.security.password.Md5CredentialsMatcher;

/**
 * 基于Agile的jdbc Realm（需要提供相关agile.plugin.security.jdbc.*配置项）
 * Created by lrp on 17-5-9.
 */
public class AgileJdbcRealm extends JdbcRealm {

    public AgileJdbcRealm(){
        super.setDataSource(DataBasePart.getDataSource());
        super.setAuthenticationQuery(SecurityConfig.getJdbcAuthcQuery());
        super.setUserRolesQuery(SecurityConfig.getJdbcRolesQuery());
        super.setPermissionsQuery(SecurityConfig.getJdbcPermissionsQuery());
        super.setPermissionsLookupEnabled(true);
        super.setCredentialsMatcher(new Md5CredentialsMatcher());//使用MD5加密算法
    }

}
