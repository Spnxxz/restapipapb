package com.example.laprak_retrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.laprak_retrofit.api.ApiConfig;
import com.example.laprak_retrofit.api.MahasiswaResponse;
import com.example.laprak_retrofit.model.Mahasiswa;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchMahasiswaActivity extends AppCompatActivity {

    private EditText edtChecNrp;
    private Button btnSearch;
    private ProgressBar progressBar;
    private TextView tvNrp;
    private TextView tvNama;
    private TextView tvEmail;
    private TextView tvJurusan;
    private List<Mahasiswa> mahasiswaList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_mahasiswa);
        edtChecNrp = findViewById(R.id.edtChckNrp);
        btnSearch = findViewById(R.id.btnSearch);
        progressBar = findViewById(R.id.progressBar);
        tvNrp = findViewById(R.id.tvValNrp);
        tvNama = findViewById(R.id.tvValNama);
        tvEmail = findViewById(R.id.tvValEmail);
        tvJurusan = findViewById(R.id.tvValJurusan);
        mahasiswaList = new ArrayList<>();
        btnSearch.setOnClickListener(view -> {
            String nrp = edtChecNrp.getText().toString();
            searchData(nrp);
        });
    }

    private void searchData(String nrp) {
        showLoading(true);
        if (nrp.isEmpty()){
            edtChecNrp.setError("Silahakan Isi nrp  terlebih dahulu");
            showLoading(false);
        }else{
            Call<MahasiswaResponse> client = ApiConfig.getApiService().getMahasiswa(nrp);
            client.enqueue(new Callback<MahasiswaResponse>() {
                @Override
                public void onResponse(Call<MahasiswaResponse> call, Response<MahasiswaResponse> response) {
                    if (response.isSuccessful()){
                        if (response.body() != null){
                            showLoading(false);
                            mahasiswaList = response.body().getData();
                            setData(mahasiswaList);
                        }
                    }else{
                        if (response.body() != null){
                            Log.e("", "onFailure: " + response.message());
                        }
                    }
                }

                @Override
                public void onFailure(Call<MahasiswaResponse> call, Throwable t) {
                    showLoading(false);
                    Log.e("Error Retrofit", "onFailure: " + t.getMessage());
                }
            });
        }
    }

    private void setData(List<Mahasiswa> mahasiswaList) {
        tvNrp.setText(mahasiswaList.get(0).getNrp());
        tvNama.setText(mahasiswaList.get(0).getNama());
        tvEmail.setText(mahasiswaList.get(0).getEmail());
        tvJurusan.setText(mahasiswaList.get(0).getJurusan());

    }

    private void showLoading(boolean b) {
        if (b){
            progressBar.setVisibility(View.VISIBLE);
        }else{
            progressBar.setVisibility(View.GONE);
        }
    }
}