package ren.lingpai.lpagiledemo.model;

import ren.lingpai.lpagiledemo.entity.DetailDO;

/**
 * Created by lrp on 17-5-19.
 */
public class DetailBO extends DetailDO {

    private String dateStr;
    private String cityName;
    private String provenceName;
    private String typeName;
    private String unitName;

    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getProvenceName() {
        return provenceName;
    }

    public void setProvenceName(String provenceName) {
        this.provenceName = provenceName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }
}
