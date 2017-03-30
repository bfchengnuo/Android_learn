package com.bfchengnuo.myapplication;

/**
 * Created by 冰封承諾Andy on 2017/3/18 0018.
 * 排序测试java bean
 */

public class Stu {
    private String name;
    private String sex;
    private int age;

    public Stu(String name, String sex, int age) {
        this.name = name;
        this.sex = sex;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String[] toStrArray(){
        String[] str = new String[]{name,sex,age+""};

        return str;
    }
}
