package ren.lingpai.lpagile.plugin.security;

import ren.lingpai.lpagile.part.ConfigPart;
import ren.lingpai.lputil.reflect.ReflectionUtil;

/**
 * 从配置文件中获取相关属性
 * Created by lrp on 17-5-9.
 */
public final class SecurityConfig {

    public static String getRealms(){
        return ConfigPart.getString(SecurityConstant.REALMS);
    }

    public static AgileSecurity getAgileSecurity(){
        String className = ConfigPart.getString(SecurityConstant.AGILE_SECURITY);
        return (AgileSecurity) ReflectionUtil.newInstance(className);
    }

    public static String getJdbcAuthcQuery(){
        return ConfigPart.getString(SecurityConstant.JDBC_AUTHC_QUERY);
    }

    public static String getJdbcRolesQuery(){
        return ConfigPart.getString(SecurityConstant.JDBC_ROLES_QUERY);
    }

    public static String getJdbcPermissionsQuery(){
        return ConfigPart.getString(SecurityConstant.JDBC_PERMISSIONS_QUERY);
    }

    public static boolean isCache(){
        return ConfigPart.getBoolean(SecurityConstant.CACHE);
    }

}
