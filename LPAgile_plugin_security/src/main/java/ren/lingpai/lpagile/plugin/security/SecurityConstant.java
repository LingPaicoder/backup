package ren.lingpai.lpagile.plugin.security;

/**
 * 常量接口
 * Created by lrp on 17-5-9.
 */
public interface SecurityConstant {

    String REALMS = "agile.plugin.security.realms";
    String REALMS_JDBC = "jdbc";
    String REALMS_CUSTOM = "custom";

    String AGILE_SECURITY = "agile.plugin.security.custom.class";

    String JDBC_AUTHC_QUERY = "agile.plugin.security.jdbc.authc_query";
    String JDBC_ROLES_QUERY = "agile.plugin.security.jdbc.roles_query";
    String JDBC_PERMISSIONS_QUERY = "agile.plugin.security.jdbc.permission_query";

    String CACHE = "agile.plugin.security.cache";

}
