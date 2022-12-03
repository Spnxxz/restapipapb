package com.example.laprak_retrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.laprak_retrofit.api.AddMahasiswaRespone;
import com.example.laprak_retrofit.api.ApiConfig;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edtNrp, edtNama, edtEmail, edtJurusan;
    private ProgressBar progressBar;
    private Button btnAdd, btnListData, btnalldata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnalldata = findViewById(R.id.btnalldata);
        edtNrp = findViewById(R.id.edtNrp);
        edtNama = findViewById(R.id.edtNama);
        edtEmail = findViewById(R.id.edtEmail);
        edtJurusan = findViewById(R.id.edtJurusan);
        progressBar = findViewById(R.id.progressBar);
        btnAdd = findViewById(R.id.btnAdd);
        btnListData = findViewById(R.id.btnList);

        btnAdd.setOnClickListener(this);
        btnListData.setOnClickListener(this);
        btnalldata.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btnAdd:
                addDataMahasiswa();
                break;
            case R.id.btnList:
                startActivity(new Intent(MainActivity.this, SearchMahasiswaActivity.class));
                break;
            case R.id.btnalldata:
                startActivity(new Intent(MainActivity.this, AllDataActivity.class));
                break;
        }
    }

    private void addDataMahasiswa() {
        showLoading(true);
        String nrp = edtNrp.getText().toString();
        String nama = edtNama.getText().toString();
        String email = edtEmail.getText().toString();
        String jurusan = edtJurusan.getText().toString();
        if (validateForm(nrp, nama, email, jurusan) == true){
            Call<AddMahasiswaRespone> client = ApiConfig.getApiService().addMahasiswa(nrp, nama, email, jurusan);
            client.enqueue(new Callback<AddMahasiswaRespone>() {
                @Override
                public void onResponse(Call<AddMahasiswaRespone> call, Response<AddMahasiswaRespone> response) {
                    showLoading(false);
                    if (response.isSuccessful()){
                        if (response.body() != null){
                            Toast.makeText(MainActivity.this, "Berhasil menambahakan  silahakan cek data pada halaman list!", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        if (response.body() != null){
                            Log.e("", "onFailure:"  + response.body().getMessage());
                        }
                    }
                }

                @Override
                public void onFailure(Call<AddMahasiswaRespone> call, Throwable t) {
                    showLoading(false);
                    Log.e("Error Retrofit", "onFailure: " +  t.getMessage());
                }
            });
        }

    }

    private boolean validateForm(String nrp, String nama, String email, String jurusan) {
        boolean result = true;
        if (nrp.isEmpty() || nama.isEmpty() || email.isEmpty() || jurusan.isEmpty()){
            Toast.makeText(MainActivity.this, "Silahkan  lengkapi form terlebih dahulu", Toast.LENGTH_SHORT).show();
            showLoading(false);
            result = false;
        }else{
            result = true;
        }
        return result;
    }


    private void showLoading(boolean b) {
        if (b){
            progressBar.setVisibility(View.VISIBLE);
        }else{
            progressBar.setVisibility(View.GONE);
        }
    }

}