package ren.lingpai.lpagiledemo.entity;

/**
 * Created by lrp on 17-6-15.
 */
public class Type_UnitDO {

    private int id;
    private int typeId;
    private int unitId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public int getUnitId() {
        return unitId;
    }

    public void setUnitId(int unitId) {
        this.unitId = unitId;
    }

    @Override
    public String toString() {
        return "Type_UnitDO{" +
                "id=" + id +
                ", typeId=" + typeId +
                ", unitId=" + unitId +
                '}';
    }
}
