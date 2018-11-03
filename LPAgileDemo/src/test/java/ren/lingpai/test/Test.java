package ren.lingpai.test;

import java.lang.reflect.Field;

/**
 * Created by lrp on 17-4-19.
 */
public class Test {

    private byte[] bytes;

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public static void main(String[] args) {
        Test test = new Test();
        Field[] fields = test.getClass().getDeclaredFields();
        for (Field field : fields){
            System.out.println("------[fieldClass]------" + field.getType());
        }
    }

}
