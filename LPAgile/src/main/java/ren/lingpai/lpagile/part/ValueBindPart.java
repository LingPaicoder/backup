package ren.lingpai.lpagile.part;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ren.lingpai.lpagile.entity.RequestParam;
import ren.lingpai.lputil.cast.CastUtil;
import ren.lingpai.lputil.collection.CollectionUtil;

import javax.sql.rowset.serial.SerialBlob;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 负责Action参数的绑定
 * Created by lrp on 17-4-29.
 */
public class ValueBindPart {

    private static final Logger LOGGER = LoggerFactory.getLogger(ValueBindPart.class);

    //用于存放可直接绑定的基本类型
    private static final Map<Class<?>, Binder<?>> MAP;

    static {
        MAP = new HashMap<Class<?>, Binder<?>>();

        Binder<?> c = new StringBinder();
        MAP.put(String.class, c);

        c = new ByteArrBinder();
        MAP.put(byte[].class,c);
        MAP.put(Byte[].class,c);

        c = new BooleanBinder();
        MAP.put(boolean.class, c);
        MAP.put(Boolean.class, c);

        c = new CharacterBinder();
        MAP.put(char.class, c);
        MAP.put(Character.class, c);

        c = new ByteBinder();
        MAP.put(byte.class, c);
        MAP.put(Byte.class, c);

        c = new ShortBinder();
        MAP.put(short.class, c);
        MAP.put(Short.class, c);

        c = new IntegerBinder();
        MAP.put(int.class, c);
        MAP.put(Integer.class, c);

        c = new LongBinder();
        MAP.put(long.class, c);
        MAP.put(Long.class, c);

        c = new FloatBinder();
        MAP.put(float.class, c);
        MAP.put(Float.class, c);

        c = new DoubleBinder();
        MAP.put(double.class, c);
        MAP.put(Double.class, c);
    }

    //判断是否为基本类型
    public static boolean canBind(Class<?> clazz) {
        return MAP.containsKey(clazz);
    }

    /**
     * 普通参数绑定
     * @param clazz
     * @param obj
     * @return
     */
    public static Object bindBasic(Class<?> clazz, Object obj){
        Binder<?> binder = MAP.get(clazz);
        return binder.bind(obj);
    }

    /**
     * 对象参数绑定
     * @param clazz
     * @return
     */
    public static Object bindObject(Class<?> clazz, RequestParam param){
        Object target = null;

        try {
            target = instantiateClass(clazz);
            //target = clazz.newInstance();
            Field[] fields = target.getClass().getDeclaredFields();
            Field[] pFields = target.getClass().getSuperclass().getDeclaredFields();
            List<Field> fieldList = new ArrayList<Field>();
            if(null != fields && fields.length > 0){
                for (Field field : fields) fieldList.add(field);
            }
            if(null != pFields && pFields.length > 0){
                for (Field field : pFields) fieldList.add(field);
            }
            if(CollectionUtil.isEmpty(fieldList)) return  null;
            for(Field field : fieldList){
                Class<?> fieldClass = field.getType();
                Object fieldValue = null;
                if(canBind(fieldClass)){
                    fieldValue = bindBasic(fieldClass,param.getFieldMap().get(field.getName()));
                }else {
                    fieldValue = param.getFieldMap().get(field.getName());
                }

                if( null != fieldValue && canBind(fieldClass)){
                    try {
                        Method m = (Method) target.getClass().getMethod("set" + getMethodName(field.getName()),field.getType());
                        m.invoke(target, fieldValue);
                    } catch (Exception e){
                        LOGGER.error("------[ValueBindPart:instantiateClass] Param Bind Error: ------targetName="
                                + target.getClass().getName()
                                + ", fieldName=" + field.getName(), e);
                    }
                }
            }
        }catch (Exception e){
            LOGGER.error("------[ValueBindPart:bindObject]exception------",e);
        }

        return target;
    }

    private static String getMethodName(String fieldName) throws Exception{
        byte[] items = fieldName.getBytes();
        items[0] = (byte) ((char) items[0] - 'a' + 'A');
        return new String(items);
    }

    public static <T> T instantiateClass(Class<T> clazz) {

        if (clazz.isInterface()) {
            LOGGER.info("------[ValueBindPart:instantiateClass] Specified class is an interface------");
        }
        try {
            return instantiateClass(clazz.getDeclaredConstructor());
        }
        catch (NoSuchMethodException ex) {
            LOGGER.info("------[ValueBindPart:instantiateClass] No default constructor found------");
        }
        return null;
    }

    public static <T> T instantiateClass(Constructor<T> ctor, Object... args)  {

        try {

            return ctor.newInstance(args);
        } catch (Exception ex) {
            LOGGER.info("------[ValueBindPart:instantiateClass] instantiateClass error------");
        }
        return null;
    }

}

interface Binder<T> {

    T bind(Object obj);

}

class ByteArrBinder implements Binder<byte[]>{
    private static final Logger LOGGER = LoggerFactory.getLogger(ByteArrBinder.class);
    public byte[] bind(Object obj){
        if(null == obj) return null;
        try {
            return CastUtil.castString(obj).getBytes("UTF-8");
        }catch (Exception e){
            LOGGER.error("------[ByteArrBinder:bind]exception------",e);
            return null;
        }
    }

}

class BooleanBinder implements Binder<Boolean> {

    public Boolean bind(Object obj) {
        if (null == obj) return  null;
        return CastUtil.castBoolean(obj);
    }

}

class ByteBinder implements Binder<Byte> {

    public Byte bind(Object obj) {
        if (null == obj) return  null;
        return CastUtil.castByte(obj);
    }

}

class CharacterBinder implements Binder<Character> {

    public Character bind(Object obj) {
        if (null == obj) return  null;
        return CastUtil.castChar(obj);
    }

}

class DoubleBinder implements Binder<Double> {

    public Double bind(Object obj) {
        if (null == obj) return  null;
        return CastUtil.castDouble(obj);
    }
}

class FloatBinder implements Binder<Float> {

    public Float bind(Object obj) {
        if (null == obj) return  null;
        return CastUtil.castFloat(obj);
    }

}

class IntegerBinder implements Binder<Integer> {

    public Integer bind(Object obj) {
        if (null == obj) return  null;
        return CastUtil.castInt(obj);
    }

}

class LongBinder implements Binder<Long> {

    public Long bind(Object obj) {
        if (null == obj) return  null;
        return CastUtil.castLong(obj);
    }

}

class ShortBinder implements Binder<Short> {

    public Short bind(Object obj) {
        if (null == obj) return  null;
        return CastUtil.castShort(obj);
    }

}

class StringBinder implements Binder<String> {

    public String bind(Object obj) {
        if (null == obj) return  null;
        return CastUtil.castString(obj);
    }

}
