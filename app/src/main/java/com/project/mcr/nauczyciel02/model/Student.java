package com.project.mcr.nauczyciel02.model;

/**
 * Created by mikolaj.mocarski on 2016-12-13.
 */
public class Student {

    private int student_id;
    private int schoolClass_id;
    private String name;
    private String email;
    private String password;

    public int getStudent_id() {
        return student_id;
    }

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }

    public int getSchoolClass_id() {
        return schoolClass_id;
    }

    public void setSchoolClass_id(int schoolClass_id) {
        this.schoolClass_id = schoolClass_id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
