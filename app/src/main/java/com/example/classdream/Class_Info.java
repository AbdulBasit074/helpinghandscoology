package com.example.classdream;

public class Class_Info {
    String Class_Name;
    String Image;
    String Class_Key;

    public Class_Info(String class_Name,String key, String image)
    {

        Class_Name = class_Name;
        Image = image;
        Class_Key = key;

    }

    public String getClass_Name() {
        return Class_Name;
    }

    public void setClass_Name(String class_Name) {
        Class_Name = class_Name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getClass_Key() {
        return Class_Key;
    }


}
