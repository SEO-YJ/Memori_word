package com.example.project;

public class Listitem {
    private String listname;
    private String listsize;

    //생성자
    Listitem(String listname, String listsize){
        this.listname = listname;
        this.listsize = listsize;
    }
    // 변수활용 위해 getter,setter
    public String getListname() {
        return listname;
    }

    public void setListname(String listname) {
        this.listname = listname;
    }

    public String getListsize() {
        return listsize;
    }

    public void setListsize(String listsize) {
        this.listsize = listsize;
    }
}
