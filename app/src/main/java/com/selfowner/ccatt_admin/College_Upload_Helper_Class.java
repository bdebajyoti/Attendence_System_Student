package com.selfowner.ccatt_admin;

public class College_Upload_Helper_Class {
    String CollegeName;
    String Address;
    String AdminPhone;
    String imageUri;
    College_Upload_Helper_Class(){

    }

    public College_Upload_Helper_Class(String collegeName, String address, String adminPhone, String imageUri) {
        CollegeName = collegeName;
        Address = address;
        AdminPhone = adminPhone;
        this.imageUri = imageUri;
    }

    public String getCollegeName() {
        return CollegeName;
    }

    public String getAddress() {
        return Address;
    }

    public String getAdminPhone() {
        return AdminPhone;
    }

    public String getImageUri() {
        return imageUri;
    }
}
