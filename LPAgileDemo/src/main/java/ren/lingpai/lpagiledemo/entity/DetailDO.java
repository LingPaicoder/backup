package ren.lingpai.lpagiledemo.entity;

import java.util.Arrays;
import java.util.Date;

/**
 * Created on 17-5-2.
 */
public class DetailDO {

    private int id;
    private String patentId;
    private Date date;
    private int cityId;
    private int provenceId;
    private int unitId;
    private int typeId;
    private short status;
    private String applier;
    private String inventor;
    private String agent;
    private String address;
    private byte[] introduce;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPatentId() {
        return patentId;
    }

    public void setPatentId(String patentId) {
        this.patentId = patentId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public int getUnitId() {
        return unitId;
    }

    public void setUnitId(int unitId) {
        this.unitId = unitId;
    }

    public short getStatus() {
        return status;
    }

    public void setStatus(short status) {
        this.status = status;
    }

    public String getApplier() {
        return applier;
    }

    public void setApplier(String applier) {
        this.applier = applier;
    }

    public String getInventor() {
        return inventor;
    }

    public void setInventor(String inventor) {
        this.inventor = inventor;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public byte[] getIntroduce() {
        return introduce;
    }

    public void setIntroduce(byte[] introduce) {
        this.introduce = introduce;
    }

    public int getProvenceId() {
        return provenceId;
    }

    public void setProvenceId(int provenceId) {
        this.provenceId = provenceId;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    @Override
    public String toString() {
        return "DetailDO{" +
                "id=" + id +
                ", patentId='" + patentId + '\'' +
                ", date=" + date +
                ", cityId=" + cityId +
                ", provenceId=" + provenceId +
                ", unitId=" + unitId +
                ", typeId=" + typeId +
                ", status=" + status +
                ", applier='" + applier + '\'' +
                ", inventor='" + inventor + '\'' +
                ", agent='" + agent + '\'' +
                ", address='" + address + '\'' +
                ", introduce=" + Arrays.toString(introduce) +
                '}';
    }
}
