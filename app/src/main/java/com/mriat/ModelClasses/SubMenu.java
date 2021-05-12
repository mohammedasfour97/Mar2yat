package com.mriat.ModelClasses;

public class SubMenu {

    public String image;
    public String title;
    public String desc;
    public String content_number;
    public String date;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String id;

    public SubMenu(String id ,String image , String  title , String desc , String content_number , String date){
        this.id = id;
        this.image= image;
        this.title = title;
        this.desc =desc;
        this.content_number = content_number;
        this.date = date;
    }

    public String getContent_number() {
        return content_number;
    }

    public String getDate() {
        return date;
    }

    public String getDesc() {
        return desc;
    }

    public String getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public void setContent_number(String content_number) {
        this.content_number = content_number;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
