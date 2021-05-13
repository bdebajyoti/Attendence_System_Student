package com.selfowner.ccatt_admin;
public class Teacher_Helper_Class {
    String TeacherName;
    String TeacherEmail;
    String TeacherCollege;
    String TeacherPassword;
    String TeacherDept;
    public Teacher_Helper_Class(){

    }

    public Teacher_Helper_Class(String teacherName, String teacherEmail, String teacherCollege,String teacherDept ,String teacherPassword ) {
        TeacherName = teacherName;
        TeacherEmail = teacherEmail;
        TeacherCollege = teacherCollege;
        TeacherDept = teacherDept;
        TeacherPassword = teacherPassword;
    }

    public String getTeacherName() {
        return TeacherName;
    }

    public String getTeacherEmail() {
        return TeacherEmail;
    }

    public String getTeacherCollege() {
        return TeacherCollege;
    }

    public String getTeacherPassword() {
        return TeacherPassword;
    }

    public String getTeacherDept() {
        return TeacherDept;
    }

    public void setTeacherName(String teacherName) {
        TeacherName = teacherName;
    }

    public void setTeacherEmail(String teacherEmail) {
        TeacherEmail = teacherEmail;
    }

    public void setTeacherCollege(String teacherCollege) {
        TeacherCollege = teacherCollege;
    }

    public void setTeacherPassword(String teacherPassword) {
        TeacherPassword = teacherPassword;
    }

    public void setTeacherDept(String teacherDept) {
        TeacherDept = teacherDept;
    }
}
