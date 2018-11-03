package ren.lingpai.lputil.cast;

import com.sun.org.apache.xpath.internal.operations.Bool;
import ren.lingpai.lputil.string.StringUtil;

/**
 * 转型操作工具类
 * Created by lrp on 17-2-4.
 */
public final class CastUtil {

    /**
     * 转为String型（提供默认值）
     * @param obj
     * @param defaultValue
     * @return
     */
    public static String castString(Object obj,String defaultValue){
        return null != obj ? String.valueOf(obj) : defaultValue;
    }

    /**
     * 转为String型（默认值为空字符串）
     * @param obj
     * @return
     */
    public static String castString(Object obj){
        return CastUtil.castString(obj,"");
    }

    /**
     * 转为float型（提供默认值）
     * @param obj
     * @param defaultFloat
     * @return
     */
    public static float castFloat(Object obj, float defaultFloat){
        float floatValue = defaultFloat;
        if(null != obj){
            String strValue = castString(obj);
            if(StringUtil.isNotEmpty(strValue)){
                try {
                    floatValue = Float.parseFloat(strValue);
                }catch (NumberFormatException e){
                    floatValue = defaultFloat;
                }
            }
        }
        return floatValue;
    }

    /**
     * 转为float型（默认值为0.0f）
     * @param obj
     * @return
     */
    public static float castFloat(Object obj){
        return CastUtil.castFloat(obj,0.0f);
    }

    /**
     * 转为double型（提供默认值）
     * @param obj
     * @param defaultValue
     * @return
     */
    public static double castDouble(Object obj,double defaultValue){
        double doubleValue = defaultValue;
        if(null != obj){
            String strValue = castString(obj);
            if (StringUtil.isNotEmpty(strValue)) {
                try{
                    doubleValue = Double.parseDouble(strValue);
                }catch(NumberFormatException e) {
                    doubleValue = defaultValue;
                }
            }
        }
        return doubleValue;
    }

    /**
     * 转为double型（默认值为0.0）
     * @param obj
     * @return
     */
    public static double castDouble(Object obj){
        return CastUtil.castDouble(obj,0.0);
    }

    /**
     * 转为long型（提供默认值）
     * @param obj
     * @param defaultValue
     * @return
     */
    public static long castLong(Object obj,long defaultValue){
        long longValue = defaultValue;
        if (obj != null) {
            String strValue = castString(obj);
            if (StringUtil.isNotEmpty(strValue)) {
                try {
                    longValue = Long.parseLong(strValue);
                } catch (NumberFormatException e) {
                    longValue = defaultValue;
                }
            }
        }
        return longValue;
    }

    /**
     * 转为long型（默认值为0）
     * @param obj
     * @return
     */
    public static long castLong(Object obj) {
        return CastUtil.castLong(obj, 0);
    }

    /**
     * 转为short型（提供默认值）
     * @param obj
     * @param defaultShort
     * @return
     */
    public static short castShort(Object obj , short defaultShort){
        short shortValue = defaultShort;
        if (obj != null) {
            String strValue = castString(obj);
            if (StringUtil.isNotEmpty(strValue)) {
                try {
                    shortValue = Short.parseShort(strValue);
                } catch (NumberFormatException e) {
                    shortValue = defaultShort;
                }
            }
        }
        return shortValue;
    }

    /**
     * 转为short型（默认值为0）
     * @param obj
     * @return
     */
    public static short castShort(Object obj){
        return CastUtil.castShort(obj , (short) 0);
    }

    /**
     * 转为int型（提供默认值）
     * @param obj
     * @param defaultValue
     * @return
     */
    public static int castInt(Object obj, int defaultValue) {
        int intValue = defaultValue;
        if (obj != null) {
            String strValue = castString(obj);
            if (StringUtil.isNotEmpty(strValue)) {
                try {
                    intValue = Integer.parseInt(strValue);
                } catch (NumberFormatException e) {
                    intValue = defaultValue;
                }
            }
        }
        return intValue;
    }

    /**
     * 转为int型（默认值为0）
     * @param obj
     * @return
     */
    public static int castInt(Object obj) {
        return CastUtil.castInt(obj, 0);
    }

    /**
     * 转为boolean型（提供默认值）
     * @param obj
     * @param defaultValue
     * @return
     */
    public static boolean castBoolean(Object obj,boolean defaultValue){
        boolean booleanValue = defaultValue;
        if(null != obj){
            booleanValue = Boolean.parseBoolean(CastUtil.castString(obj));
        }
        return booleanValue;
    }

    /**
     * 转为boolean型（默认为false）
     * @param obj
     * @return
     */
    public static boolean castBoolean(Object obj) {
        return CastUtil.castBoolean(obj, false);
    }

    /**
     * 转为byte型（提供默认值）
     * @param obj
     * @param defaultByte
     * @return
     */
    public static byte castByte(Object obj , byte defaultByte){
        byte byteValue = defaultByte;
        if(null != obj){
            byteValue = Byte.parseByte(CastUtil.castString(obj));
        }
        return byteValue;
    }

    /**
     * 转为byte型（默认为0）
     * @param obj
     * @return
     */
    public static byte castByte(Object obj){
        return CastUtil.castByte(obj , (byte)0);
    }

    /**
     * 转为char型
     * @param obj
     * @return
     */
    public static char castChar(Object obj ){
        String s = CastUtil.castString(obj);
        if (s.length()==0)
            throw new IllegalArgumentException("------[CastUtil:castChar]------Cannot convert empty string to char.");
        return s.charAt(0);
    }

}
