package com.mini.btechhub;

public class RegisterHelperClass {
     String user,selected_semester,selected_branch;

    public RegisterHelperClass(){

    }

    public String getSelected_branch() {
        return selected_branch;
    }

    public void setSelected_branch(String selected_branch) {
        this.selected_branch = selected_branch;
    }

    public String getSelected_semester() {
        return selected_semester;
    }

    public void setSelected_semester(String selected_semester) {
        this.selected_semester = selected_semester;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}