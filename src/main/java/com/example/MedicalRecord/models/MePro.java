package com.example.MedicalRecord.models;

import java.io.Serializable;

public class MePro implements Serializable {
    int UserID;
    String patientID;
    String patientName;
    String dateofbirth;
    String gender;
    String address;
    String phoneNumber;

    public MePro(int userID, String patientID, String patientName, String dateofbirth, String gender, String address, String phoneNumber) {
        UserID = userID;
        this.patientID = patientID;
        this.patientName = patientName;
        this.dateofbirth = dateofbirth;
        this.gender = gender;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }

    public String getPatientID() {
        return patientID;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }


    public String getDateofbirth() {
        return dateofbirth;
    }

    public void setDateofbirth(String dateofbirth) {
        this.dateofbirth = dateofbirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
