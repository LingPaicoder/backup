package ren.lingpai.lpagiledemo.entity;

/**
 * Created on 17-5-2.
 */
public class CityDO {

    private int id;
    private int provenceId;
    private String cityName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProvenceId() {
        return provenceId;
    }

    public void setProvenceId(int provenceId) {
        this.provenceId = provenceId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
