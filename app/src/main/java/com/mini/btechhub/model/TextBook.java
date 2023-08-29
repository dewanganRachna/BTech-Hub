package com.mini.btechhub.model;

public class TextBook {
    String chaptername,chapter;

    public TextBook(String chaptername,String chapter ){
        this.chaptername=chaptername;
        this.chapter=chapter;
    }

    public String getChaptername() {
        return chaptername;
    }

    public String getChapter() {
        return chapter;
    }
}
