package ren.lingpai.lputil.clazz;

import javassist.*;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ren.lingpai.lputil.cast.CastUtil;
import ren.lingpai.lputil.collection.CollectionUtil;
import ren.lingpai.lputil.string.StringUtil;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 类加载工具类
 * Created by lrp on 17-2-4.
 */
public final class ClassUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClassUtil.class);

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
     * 获取类加载器
     * @return
     */
    public static ClassLoader getClassLoader(){
        return Thread.currentThread().getContextClassLoader();
    }

    /**
     * 加载类
     * @param className
     * @param isInitialized
     * @return
     */
    public static Class<?> loadClass(String className,boolean isInitialized){
        Class<?> cls;
        try{
            cls = Class.forName(className,isInitialized,getClassLoader());
        }catch (ClassNotFoundException e){
            LOGGER.error("load class failure",e);
            throw new RuntimeException(e);
        }
        return cls;
    }

    /**
     * 加载类（默认初始化类）
     * @param className
     * @return
     */
    public static Class<?> loadClass(String className){
        return loadClass(className,true);
    }

    /**
     * 将JavaBean转为Map
     * @param obj
     * @return
     * @throws Exception
     */
    public static Map<String,Object> convertBeanToMap(Object obj) throws Exception{
        Map<String,Object> result = new HashMap<String, Object>();
        Field[] fields = obj.getClass().getDeclaredFields();
        Field[] pFields = obj.getClass().getSuperclass().getDeclaredFields();
        List<Field> fieldList = new ArrayList<Field>();
        if(null != fields && fields.length > 0){
            for (Field field : fields) fieldList.add(field);
        }
        if(null != pFields && pFields.length > 0){
            for (Field field : pFields) fieldList.add(field);
        }
        if(CollectionUtil.isNotEmpty(fieldList)){
            for(Field field : fieldList){
                try {
                    Method m = (Method) obj.getClass().getMethod("get" + getMethodName(field.getName()));
                    result.put(field.getName(),m.invoke(obj));
                } catch (Exception e){
                    LOGGER.error("------[ClassUtil:convertBeanToMap]exception------", e);
                }

            }
        }else {
            result = null;
        }
        return result;
    }

    private static String getMethodName(String fieldName) throws Exception{
        byte[] items = fieldName.getBytes();
        items[0] = (byte) ((char) items[0] - 'a' + 'A');
        return new String(items);
    }

    /**
     * 得到方法参数名称数组
     * 由于java没有提供获得参数名称的api，利用了javassist来实现
     * @param clazz
     * @param method
     * @return
     */
    public static String[] getMethodParamNames(Class<?> clazz, Method method) {
        Class<?>[] paramTypes = method.getParameterTypes();

        try {
            ClassPool pool = ClassPool.getDefault();

            pool.insertClassPath(new ClassClassPath(clazz));

            CtClass cc = pool.get(clazz.getName());

            String[] paramTypeNames = new String[method.getParameterTypes().length];

            for (int i = 0; i < paramTypes.length; i++)
                paramTypeNames[i] = paramTypes[i].getName();

            CtMethod cm = cc.getDeclaredMethod(method.getName(), pool.get(paramTypeNames));

            // 使用javaassist的反射方法获取方法的参数名
            MethodInfo methodInfo = cm.getMethodInfo();

            CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
            LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute
                    .getAttribute(LocalVariableAttribute.tag);
            if (attr == null) {
                throw new RuntimeException("class:" + clazz.getName()
                        + ", have no LocalVariableTable, please use javac -g:{vars} to compile the source file");
            }

            int startIndex = getStartIndex(attr);
            String[] paramNames = new String[cm.getParameterTypes().length];
            int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;

            for (int i = 0; i < paramNames.length; i++)
                paramNames[i] = attr.variableName(startIndex + i + pos);

            return paramNames;

        } catch (NotFoundException e) {
            e.printStackTrace();
            return new String[0];
        }
    }

    private static int getStartIndex(LocalVariableAttribute attr) {

        int startIndex = 0;
        for (int i = 0; i < attr.length(); i++) {
            if ("this".equals(attr.variableName(i))) {
                startIndex = i;
                break;
            }
        }
        return startIndex;
    }

    /**
     * 获取指定包下的所有类
     * @param packageName
     * @return
     */
    public static Set<Class<?>> getClassSet(String packageName){
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        try {
            Enumeration<URL> urls = getClassLoader().getResources(packageName.replace(".","/"));
            while (urls.hasMoreElements()){
                URL url = urls.nextElement();
                if(null != url){
                    String protocol = url.getProtocol();
                    if("file".equals(protocol)){
                        //class文件方式加载
                        String packagePath = url.getPath().replaceAll("%20"," ");
                        addClassByClassFile(classSet,packagePath,packageName);
                    }else if("jar".equals(protocol)){
                        //jar包方式加载
                        addClassByJarFile(classSet,url);
                    }
                }
            }
        }catch (Exception e){
            LOGGER.error("get class set failure",e);
            throw new RuntimeException(e);
        }
        return classSet;
    }

    /**
     * 将属性从from复制到to中(只支持基本类型)
     * @return
     */
    public static void copyField(Map<String,Object> from , Object to) throws Exception {
        Field[] fields = to.getClass().getDeclaredFields();
        if((null == fields) || (0 == fields.length)) return ;
        for(Field field : fields){
            Class<?> fieldClass = field.getType();
            Object fieldValue = null;
            if(canBind(fieldClass)){
                fieldValue = from.get(field.getName());
            }
            if( null != fieldValue && canBind(fieldClass)){
                Method m = (Method) to.getClass().getMethod("set" + getMethodName(field.getName()),field.getType());
                m.invoke(to, fieldValue);
            }
        }
        return ;
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
     * class文件方式加载
     * @param classSet
     * @param packagePath
     * @param packageName
     */
    private static void addClassByClassFile(Set<Class<?>> classSet,String packagePath,String packageName){
        //获取所有目录和以.class结尾的文件
        File[] files = new File(packagePath).listFiles(new FileFilter() {
            public boolean accept(File file) {
                return (file.isFile() && file.getName().endsWith(".class")) || file.isDirectory();
            }
        });
        for(File file:files){
            String fileName = file.getName();
            if(file.isFile()){
                //文件
                String className = fileName.substring(0,fileName.lastIndexOf("."));
                if(StringUtil.isNotEmpty(packageName)){
                    className = packageName + "." + className;
                }
                doAddClass(classSet,className);
            }else {
                //目录
                String subPackagePath = fileName;
                if(StringUtil.isNotEmpty(packagePath)){
                    subPackagePath = packagePath + "/" + subPackagePath;
                }
                String subPackageName = fileName;
                if(StringUtil.isNotEmpty(packageName)){
                    subPackageName = packageName + "." + subPackageName;
                }
                addClassByClassFile(classSet,subPackagePath,subPackageName);
            }

        }
    }

    /**
     * jar包方式加载
     * @param url
     */
    private static void addClassByJarFile(Set<Class<?>> classSet,URL url) throws IOException{
        JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
        if(null != jarURLConnection){
            JarFile jarFile = jarURLConnection.getJarFile();
            if (null != jarFile){
                Enumeration<JarEntry> jarEntries = jarFile.entries();
                while (jarEntries.hasMoreElements()){
                    JarEntry jarEntry = jarEntries.nextElement();
                    String jarEntryName = jarEntry.getName();
                    if(jarEntryName.endsWith(".class")){
                        String className = jarEntryName.substring(0,jarEntryName.lastIndexOf(".")).replace("/",".");
                        doAddClass(classSet,className);
                    }
                }
            }
        }
    }

    /**
     * 将Class对象添加到classSet中
     * 供addClassByClassFile和addClassByJarFile方法调用
     * @param classSet
     * @param className
     */
    private static void doAddClass(Set<Class<?>> classSet,String className){
        Class<?> cls = loadClass(className,false);
        classSet.add(cls);
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
