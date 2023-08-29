package com.mini.btechhub;

import android.content.Context;
import android.content.Intent;

public class HelperClass {

    String name ,email,username,phoneno,selected_semester,selected_branch;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    /*public String getSelected_branch() {
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
    }*/

    /*public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }*/

    public HelperClass(String name, String email, String username,String phonenumber) {
        this.name = name;
        this.email = email;
        this.username = username;
        //this.password = password;
        this.phoneno=phonenumber;

    }

    public HelperClass() {
    }
}


