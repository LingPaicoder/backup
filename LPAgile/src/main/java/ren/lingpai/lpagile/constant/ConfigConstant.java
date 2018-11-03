package ren.lingpai.lpagile.constant;

/**
 * 提供配置项常量
 * Created by lrp on 17-1-14.
 */
public interface ConfigConstant {

    String CONFIG_FILE = "agile.properties";

    String JDBC_DRIVER = "agile.jdbc.driver";
    String JDBC_URL = "agile.jdbc.url";
    String JDBC_USERNAME = "agile.jdbc.username";
    String JDBC_PASSWORD = "agile.jdbc.password";

    //基础包名
    String APP_BASE_PACKAGE = "agile.app.base_package";
    //JSP的基础路径
    String APP_JSP_PATH = "agile.app.jsp_path";
    //静态资源文件的基础路径
    String APP_ASSET_PATH = "agile.app.asset_path";
    //应用文件上传限制
    String APP_UPLOAD_LIMIT = "agile.app.upload_limit";

}
