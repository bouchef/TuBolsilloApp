package com.example.bouchef.tubolsillo.api.model;

import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * POJO para los mensajes de Compras
 */

public class MensajesCompraDisplayList {

    // estados:
    public static List<String> STATES_VALUES =
            Arrays.asList("Todas", "No Leidos", "Leidos");

    @SerializedName("id")
    private int mId;
    @SerializedName("date_and_time")
    private Date mDateAndTime;
    @SerializedName("descripcion")
    private String mDescripcion;
    @SerializedName("status")
    private String mStatus;
    @SerializedName("doctor")
    private String mDoctor;
    @SerializedName("medical_center")
    private String mMedicalCenter;

    public MensajesCompraDisplayList(int id, Date dateAndTime, String descripcion,
                                     String status, String doctor, String medicalCenter) {
        mId = id;
        mDateAndTime = dateAndTime;
        mDescripcion = descripcion;
        mStatus = status;
        mDoctor = doctor;
        mMedicalCenter = medicalCenter;
    }

    public int getId() {
        return mId;
    }

    public Date getDateAndTime() {
        return mDateAndTime;
    }

    public String getDateAndTimeForList() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd \n HH:mm:ss", Locale.getDefault());
        return sdf.format(mDateAndTime);
    }

    public String getDescripcion() {
        return mDescripcion;
    }

    public String getStatus() {
        return mStatus;
    }

    public String getDoctor() {
        return mDoctor;
    }

    public String getMedicalCenter() {
        return mMedicalCenter;
    }

    public void setId(int mId) {
        this.mId = mId;
    }

    public void setDateAndTime(Date mDateAndTime) {
        this.mDateAndTime = mDateAndTime;
    }

    public void setService(String mDescripcion) {
        this.mDescripcion = mDescripcion;
    }

    public void setStatus(String mStatus) {
        this.mStatus = mStatus;
    }

    public void setDoctor(String mDoctor) {
        this.mDoctor = mDoctor;
    }

    public void setMedicalCenter(String mMedicalCenter) {
        this.mMedicalCenter = mMedicalCenter;
    }
}
