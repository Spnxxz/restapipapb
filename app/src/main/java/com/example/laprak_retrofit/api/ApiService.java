package com.example.laprak_retrofit.api;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface ApiService {
    @GET("mahasiswa")
    Call<MahasiswaResponse> getMahasiswa(@Query("nrp") String nrp);

    @GET("mahasiswa")
    Call<MahasiswaResponse> getMahasiswabyid(@Query("id") String id);

    @POST("mahasiswa")
    @FormUrlEncoded
    Call<AddMahasiswaRespone> addMahasiswa(
            @Field("nrp") String nrp,
            @Field("nama") String nama,
            @Field("email") String email,
            @Field("jurusan") String jurusan
    );

    @GET("mahasiswa")
    Call<MahasiswaResponse> getMahasiswa();

    @PUT("mahasiswa")
    @FormUrlEncoded
    Call<AddMahasiswaRespone> updateMahasiswa(
            @Field("id") String id,
            @Field("nrp") String nrp,
            @Field("nama") String nama,
            @Field("email") String email,
            @Field("jurusan") String jurusan
    );

    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "mahasiswa", hasBody = true)
    Call<AddMahasiswaRespone> deleteMahasiswa(@Field("id")String id);
}
