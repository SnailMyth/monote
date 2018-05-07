package com.myth.test;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;

public class TestModel {
    private String s;
    private Integer i;
    private int[] ints;
    private Date date;
    private List list;
    private char[] chars;

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public Integer getI() {
        return i;
    }

    public void setI(Integer i) {
        this.i = i;
    }

    public int[] getInts() {
        return ints;
    }

    public void setInts(int[] ints) {
        this.ints = ints;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

    public char[] getChars() {
        return chars;
    }

    public void setChars(char[] chars) {
        this.chars = chars;
    }

    public static void main(String[] strs) {
        TestModel testModel = new TestModel();
        Field[] declaredFields = testModel.getClass().getDeclaredFields();
        for (Field f :
                declaredFields) {
            System.out.println(f.getGenericType().getTypeName());
            if (f.getGenericType().getTypeName().equals("java.util.List")){

            }
        }

    }
}
