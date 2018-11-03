package ren.lingpai.lpagile.entity;

import ren.lingpai.lputil.cast.CastUtil;
import ren.lingpai.lputil.collection.CollectionUtil;
import ren.lingpai.lputil.string.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 请求参数
 * Created by lrp on 17-2-9.
 */
public class RequestParam {

    private List<RequestFileParam> fileParamList;

    private List<RequestFormParam> formParamList;

    public RequestParam(List<RequestFormParam> formParamList){
        this.formParamList = formParamList;
    }

    public RequestParam(List<RequestFormParam> formParamList, List<RequestFileParam> fileParamList){
        this.formParamList = formParamList;
        this.fileParamList = fileParamList;
    }

    /**
     * 获取请求参数映射
     * @return
     */
    public Map<String,Object> getFieldMap(){
        Map<String,Object> fieldMap = new HashMap<String, Object>();
        if(CollectionUtil.isNotEmpty(formParamList)){
            for(RequestFormParam formParam : formParamList){
                String fieldName = formParam.getFieldName();
                Object fieldValue = formParam.getFieldValue();
                if(fieldMap.containsKey(fieldName)){
                    fieldValue = fieldMap.get(fieldName) + StringUtil.SEPARATOR + fieldValue;
                }
                fieldMap.put(fieldName,fieldValue);
            }
        }
        return fieldMap;
    }

    /**
     * 获取上传文件映射
     * @return
     */
    public Map<String,List<RequestFileParam>> getFileMap(){
        Map<String,List<RequestFileParam>> fileMap = new HashMap<String, List<RequestFileParam>>();
        if(CollectionUtil.isNotEmpty(fileParamList)){
            for(RequestFileParam fileParam : fileParamList){
                String fieldName = fileParam.getFieldName();
                List<RequestFileParam> fileParamList;
                if(fileMap.containsKey(fieldName)){
                    fileParamList = fileMap.get(fieldName);
                }else{
                    fileParamList = new ArrayList<RequestFileParam>();
                }
                fileParamList.add(fileParam);
                fileMap.put(fieldName,fileParamList);
            }
        }
        return fileMap;
    }

    /**
     * 根据表单字段名获取所有上传文件
     * @param fieldName
     * @return
     */
    public List<RequestFileParam> getFileList(String fieldName){
        return getFileMap().get(fieldName);
    }

    /**
     * 根据表单字段名获取唯一上传文件
     * @param fieldName
     * @return
     */
    public RequestFileParam getFile(String fieldName){
        List<RequestFileParam> fileParamList = getFileList(fieldName);
        if(CollectionUtil.isNotEmpty(fileParamList) && (1 == fileParamList.size())){
            return fileParamList.get(0);
        }
        return null;
    }

    /**
     * 严重Param是否为空
     * @return
     */
    public boolean isEmpty(){
        return CollectionUtil.isEmpty(formParamList) && CollectionUtil.isEmpty(fileParamList);
    }

    /**
     * 根据参数名获取 String 型参数值
     * @param name
     * @return
     */
    public String getString(String name) {
        return CastUtil.castString(getFieldMap().get(name));
    }

    /**
     * 根据参数名获取 double 型参数值
     * @param name
     * @return
     */
    public double getDouble(String name) {
        return CastUtil.castDouble(getFieldMap().get(name));
    }

    /**
     * 根据参数名获取 long 型参数值
     * @param name
     * @return
     */
    public long getLong(String name) {
        return CastUtil.castLong(getFieldMap().get(name));
    }

    /**
     *
     *
     * 根据参数名获取 int 型参数值
     * @param name
     * @return
     */
    public int getInt(String name) {
        return CastUtil.castInt(getFieldMap().get(name));
    }

    /**
     * 根据参数名获取 boolean 型参数值
     * @param name
     * @return
     */
    public boolean getBoolean(String name) {
        return CastUtil.castBoolean(getFieldMap().get(name));
    }


}
