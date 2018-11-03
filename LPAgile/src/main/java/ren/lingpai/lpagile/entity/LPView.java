package ren.lingpai.lpagile.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回视图对象
 * Created by lrp on 17-2-9.
 */
public class LPView {

    /**
     * 视图路径
     */
    private String path;

    /**
     * 模型数据
     */
    private Map<String,Object> model;

    public LPView(String path){
        this.path = path;
        model = new HashMap<String, Object>();
    }

    public LPView addModel(String key, Object value){
        model.put(key,value);
        return this;
    }

    public String getPath() {
        return path;
    }

    public Map<String, Object> getModel() {
        return model;
    }
}
