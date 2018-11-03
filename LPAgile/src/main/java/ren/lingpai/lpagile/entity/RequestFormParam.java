package ren.lingpai.lpagile.entity;

/**
 * 表单参数
 * Created by lrp on 17-4-18.
 */
public class RequestFormParam {

    private String fieldName;
    private Object fieldValue;

    public RequestFormParam(String fieldName, Object fieldValue){
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public Object getFieldValue() {
        return fieldValue;
    }

    public String getFieldName() {
        return fieldName;
    }
}
