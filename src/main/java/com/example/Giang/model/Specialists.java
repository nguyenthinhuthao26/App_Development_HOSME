package com.example.Giang.model;

import java.io.Serializable;

public class Specialists implements Serializable {
    String specialistID;
    String specialistName;
    String specialistDescription;
    byte[] specialistImage;

    public Specialists(String specialistID, String specialistName, String specialistDescription, byte[] specialistImage) {
        this.specialistID = specialistID;
        this.specialistName = specialistName;
        this.specialistDescription = specialistDescription;
        this.specialistImage = specialistImage;
    }

    public String getSpecialistID() {
        return specialistID;
    }

    public void setSpecialistID(String specialistID) {
        this.specialistID = specialistID;
    }

    public String getSpecialistName() {
        return specialistName;
    }

    public void setSpecialistName(String specialistName) {
        this.specialistName = specialistName;
    }

    public String getSpecialistDescription() {
        return specialistDescription;
    }

    public void setSpecialistDescription(String specialistDescription) {
        this.specialistDescription = specialistDescription;
    }

    public byte[] getSpecialistImage() {
        return specialistImage;
    }

    public void setSpecialistImage(byte[] specialistImage) {
        this.specialistImage = specialistImage;
    }
}
