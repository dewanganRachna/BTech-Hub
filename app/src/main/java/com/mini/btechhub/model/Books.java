package com.mini.btechhub.model;


public class Books {
    String title,cover,content,type;

    public Books(String title,String cover,String content,String type){
        this.title=title;
        this.cover=cover;
        this.content=content;
        this.type=type;

    }
    public String getTitle(){

        return title;
    }


    public String getCover() {

        return cover;
    }

    public String getContent()
    {
        return content;
    }

    public String getType() {

        return type;
    }
}
