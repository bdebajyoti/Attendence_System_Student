package com.selfowner.ccatt_admin;

public class Data_Upload_Helper_Class {
    String Semester;
    String Subject;
    String Stream;
    String CourseType;

    public Data_Upload_Helper_Class(){
        Semester="";
        Subject="";
        Stream="";
        CourseType="";
    }

    public Data_Upload_Helper_Class(String semester, String subject, String stream, String courseType) {
        this.Semester = semester;
        this.Subject = subject;
        this.Stream = stream;
        this.CourseType = courseType;
    }

    public String getSemester() {
        return Semester;
    }

    public String getSubject() {
        return Subject;
    }

    public String getStream() {
        return Stream;
    }

    public String getCourseType() {
        return CourseType;
    }
}
