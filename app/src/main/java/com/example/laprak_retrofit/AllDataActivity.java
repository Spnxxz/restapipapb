package com.example.laprak_retrofit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.laprak_retrofit.api.AddMahasiswaRespone;
import com.example.laprak_retrofit.api.ApiConfig;
import com.example.laprak_retrofit.api.MahasiswaResponse;
import com.example.laprak_retrofit.api.hapus;
import com.example.laprak_retrofit.model.Mahasiswa;
import com.example.laprak_retrofit.recycleview.Adapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllDataActivity extends AppCompatActivity {
    ImageButton back;
    RecyclerView recyclerView;
    Adapter adapter;
    List<Mahasiswa> mahasiswas = new ArrayList<Mahasiswa>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_mahasiswa);
        back = findViewById(R.id.btnbck);
        recyclerView = findViewById(R.id.recycleview);
        showData();
        back.setOnClickListener(view -> {
            startActivity(new Intent(AllDataActivity.this, MainActivity.class));
        });
    }

    private void recycleview(){
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Adapter(mahasiswas, AllDataActivity.this, listener);
        recyclerView.setAdapter(adapter);
    }

    private final hapus listener = new hapus() {
        @Override
        public void onClickdelete(Mahasiswa mahasiswa, Button imageButton) {
            Call<AddMahasiswaRespone> client = ApiConfig.getApiService().deleteMahasiswa(mahasiswa.getId());
            client.enqueue(new Callback<AddMahasiswaRespone>() {
                @Override
                public void onResponse(Call<AddMahasiswaRespone> call, Response<AddMahasiswaRespone> response) {
                    if (response.isSuccessful()){
                        if (response != null){
                            Toast.makeText(getApplicationContext(), "Data berhasil dihapus", Toast.LENGTH_SHORT).show();
                            showData();
                            adapter.notifyDataSetChanged();
                        }
                    }else if (response.body() != null) {
                            Log.e("", "onFailure:" + response.body().getMessage());
                        }
                }

                @Override
                public void onFailure(Call<AddMahasiswaRespone> call, Throwable t) {
                    Log.e("", "Failure: " + t.getMessage());
                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    };

    private void showData(){
        Call<MahasiswaResponse> client = ApiConfig.getApiService().getMahasiswa();
        client.enqueue(new Callback<MahasiswaResponse>() {
            @Override
            public void onResponse(Call<MahasiswaResponse> call, Response<MahasiswaResponse> response) {
                if (response.isSuccessful()){
                    if (response.body() != null){
                        mahasiswas.addAll(response.body().getData());
                        if (mahasiswas.isEmpty()){
                            Toast.makeText(getApplicationContext(), "gagal", Toast.LENGTH_SHORT).show();
                        }
                        recycleview();
                    }
                }else if (response.body() != null){
                    Log.e("", "Failure: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<MahasiswaResponse> call, Throwable t) {
                Log.e("", "Failure: " + t.getMessage());
            }
        });
    }
}