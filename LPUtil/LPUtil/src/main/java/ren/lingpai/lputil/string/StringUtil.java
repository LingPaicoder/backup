package ren.lingpai.lputil.string;

import org.apache.commons.lang3.StringUtils;

/**
 * 字符串工具类
 * Created by lrp on 17-2-4.
 */
public final class StringUtil {

    /**
     * 字符串分隔符
     */
    public static final String SEPARATOR = String.valueOf((char) 29);

    /**
     * 判断字符串是否为空("    "也算为空)
     * @param str
     * @return
     */
    public static boolean isEmpty(String str){
        return null == str || str.trim().length() == 0;
    }

    /**
     * 判断字符串是否为空("    "不算为空)
     * @param str
     * @return
     */
    public static boolean isSimpleEmpty(String str){
        return null == str || str.length() == 0;
    }

    /**
     * 判断字符串是否不空("    "也算为空)
     * @param str
     * @return
     */
    public static boolean isNotEmpty(String str){
        return !isEmpty(str);
    }

    /**
     * 判断字符串是否不空("    "不算为空)
     * @param str
     * @return
     */
    public static boolean isNotSimpleEmpty(String str){
        return !isSimpleEmpty(str);
    }

    /**
     * 分割固定格式的字符串(包括“&”等也可以做分隔符)
     * @param str
     * @param separator
     * @return
     */
    public static String[] splitString(String str,String separator){
        return StringUtils.splitByWholeSeparator(str,separator);
    }


}
