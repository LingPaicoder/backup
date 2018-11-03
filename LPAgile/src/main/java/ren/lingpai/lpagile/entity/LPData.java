package ren.lingpai.lpagile.entity;

/**
 * 返回数据对象
 * Created by lrp on 17-2-9.
 */
public class LPData {

    /**
     * 模型数据
     */
    private Object model;

    public LPData(Object model){
        this.model = model;
    }

    public Object getModel() {
        return model;
    }
}
