package ren.lingpai.lpagiledemo.entity;

/**
 * Created on 17-5-2.
 */
public class ProvenceDO {

    private int provenceId;
    private String provenceName;
    private int capital;

    public int getCapital() {
        return capital;
    }

    public void setCapital(int capital) {
        this.capital = capital;
    }

    public int getProvenceId() {
        return provenceId;
    }

    public void setProvenceId(int provenceId) {
        this.provenceId = provenceId;
    }

    public String getProvenceName() {
        return provenceName;
    }

    public void setProvenceName(String provenceName) {
        this.provenceName = provenceName;
    }
}
