package com.example.laprak_retrofit.api;

import com.example.laprak_retrofit.model.Mahasiswa;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MahasiswaResponse {

    @SerializedName("data")
    private List<Mahasiswa> data;

    @SerializedName("status")
    private boolean status;

    public List<Mahasiswa> getData() {
        return data;
    }

    public boolean isStatus() {
        return status;
    }
}
