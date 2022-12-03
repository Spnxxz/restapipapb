package com.example.laprak_retrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.laprak_retrofit.api.AddMahasiswaRespone;
import com.example.laprak_retrofit.api.ApiConfig;
import com.example.laprak_retrofit.api.MahasiswaResponse;
import com.example.laprak_retrofit.model.Mahasiswa;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateActivity extends AppCompatActivity {
    private EditText edtNrp, edtNama, edtEmail, edtJurusan;
    private Button btnupdate;
    List<Mahasiswa> mahasiswas = new ArrayList<>();
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        edtNrp = findViewById(R.id.edtNrp);
        edtNama = findViewById(R.id.edtNama);
        edtEmail = findViewById(R.id.edtEmail);
        edtJurusan = findViewById(R.id.edtJurusan);
        btnupdate = findViewById(R.id.btnupdate);
        Intent i = getIntent();
        id = i.getStringExtra("id");
        showOldData(id);
        btnupdate.setOnClickListener(view -> {
            updateData();
        });
    }

    private void showOldData(String id) {
        Call<MahasiswaResponse> Client = ApiConfig.getApiService().getMahasiswabyid(id);
        Client.enqueue(new Callback<MahasiswaResponse>() {
            @Override
            public void onResponse(Call<MahasiswaResponse> call, Response<MahasiswaResponse> response) {
                if (response.isSuccessful()){
                    mahasiswas = response.body().getData();
                    setData(mahasiswas);
                }
            }

            @Override
            public void onFailure(Call<MahasiswaResponse> call, Throwable t) {

            }
        });
    }

    private void updateData() {
        String nrp = edtNrp.getText().toString();
        String nama = edtNama.getText().toString();
        String email = edtEmail.getText().toString();
        String jurusan = edtJurusan.getText().toString();
        if (validateForm(nrp, nama, email, jurusan) == true){
            Call<AddMahasiswaRespone> Client = ApiConfig.getApiService().updateMahasiswa(id,nrp, nama, email, jurusan);
            Client.enqueue(new Callback<AddMahasiswaRespone>() {
                @Override
                public void onResponse(Call<AddMahasiswaRespone> call, Response<AddMahasiswaRespone> response) {
                    if (response.isSuccessful()){
                        if (response.body() != null){
                            Toast.makeText(UpdateActivity.this, "Berhasil update data silahakan cek data pada halaman list!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), AllDataActivity.class));
                        }
                    }else{
                        if (response.body() != null){
                            Log.e("", "onFailure:"  + response.body().getMessage());
                        }
                    }
                }

                @Override
                public void onFailure(Call<AddMahasiswaRespone> call, Throwable t) {
                    Log.e("Error Retrofit", "onFailure: " +  t.getMessage());
                }
            });
        }
    }

    private void setData(List<Mahasiswa> mahasiswaList) {
        edtNrp.setText(mahasiswaList.get(0).getNrp());
        edtNama.setText(mahasiswaList.get(0).getNama());
        edtEmail.setText(mahasiswaList.get(0).getEmail());
        edtJurusan.setText(mahasiswaList.get(0).getJurusan());

    }

    private boolean validateForm(String nrp, String nama, String email, String jurusan) {
        boolean result = true;
        if (nrp.isEmpty() || nama.isEmpty() || email.isEmpty() || jurusan.isEmpty()){
            Toast.makeText(UpdateActivity.this, "Silahkan  lengkapi form terlebih dahulu", Toast.LENGTH_SHORT).show();
            result = false;
        }else{
            result = true;
        }
        return result;
    }
}