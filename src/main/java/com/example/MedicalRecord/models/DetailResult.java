package com.example.MedicalRecord.models;

import java.io.Serializable;

public class DetailResult implements Serializable {
    int MedicalId;
    int TestId;
    String patientID;
    String patientName;
    String mainExam;
    String dateTimeIn;
    String dateTimeOut;
    byte[] resultImage;
    String address;
    String doctor;


    public DetailResult(int medicalId, int testId, String patientID, String patientName, String mainExam, String dateTimeIn, String dateTimeOut, byte[] resultImage, String address, String doctor) {
        MedicalId = medicalId;
        TestId = testId;
        this.patientID = patientID;
        this.patientName = patientName;
        this.mainExam = mainExam;
        this.dateTimeIn = dateTimeIn;
        this.dateTimeOut = dateTimeOut;
        this.resultImage = resultImage;
        this.address = address;
        this.doctor = doctor;
    }

    public int getMedicalId() {
        return MedicalId;
    }

    public void setMedicalId(int medicalId) {
        MedicalId = medicalId;
    }

    public int getTestId() {
        return TestId;
    }

    public void setTestId(int testId) {
        TestId = testId;
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

    public String getMainExam() {
        return mainExam;
    }

    public void setMainExam(String mainExam) {
        this.mainExam = mainExam;
    }

    public String getDateTimeIn() {
        return dateTimeIn;
    }

    public void setDateTimeIn(String dateTimeIn) {
        this.dateTimeIn = dateTimeIn;
    }

    public String getDateTimeOut() {
        return dateTimeOut;
    }

    public void setDateTimeOut(String dateTimeOut) {
        this.dateTimeOut = dateTimeOut;
    }

    public byte[] getResultImage() {
        return resultImage;
    }

    public void setResultImage(byte[] resultImage) {
        this.resultImage = resultImage;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }
}