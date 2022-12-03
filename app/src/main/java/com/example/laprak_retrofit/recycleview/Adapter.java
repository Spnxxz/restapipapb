package com.example.laprak_retrofit.recycleview;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.laprak_retrofit.R;
import com.example.laprak_retrofit.UpdateActivity;
import com.example.laprak_retrofit.api.hapus;
import com.example.laprak_retrofit.model.Mahasiswa;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.Viewholder> {
    List<Mahasiswa> mahasiswaArrayList = new ArrayList<>();
    Activity context;
    hapus hapus;

    public Adapter(List<Mahasiswa> mahasiswaArrayList, Activity context, hapus Hapus) {
        this.mahasiswaArrayList = mahasiswaArrayList;
        this.context = context;
        this.hapus = Hapus;
    }

    @NonNull
    @Override
    public Adapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.Viewholder holder, @SuppressLint("RecyclerView") int position) {
        Mahasiswa mahasiswa = mahasiswaArrayList.get(position);
        holder.nrp.setText("nrp: " + mahasiswa.getNrp());
        holder.email.setText("email: " + mahasiswa.getEmail());
        holder.nama.setText("nama: " + mahasiswa.getNama());
        holder.jurusan.setText("jurusan: " + mahasiswa.getJurusan());
        holder.edit.setOnClickListener(view -> {
            Intent i = new Intent(context, UpdateActivity.class);
            i.putExtra("id", mahasiswaArrayList.get(holder.getAdapterPosition()).getId());
            context.startActivity(i);
        });
        holder.delete.setOnClickListener(view -> {
            hapus.onClickdelete(mahasiswaArrayList.get(holder.getAdapterPosition()), holder.delete);
        });

    }

    @Override
    public int getItemCount() {
        return mahasiswaArrayList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView nrp, nama, email, jurusan;
        Button edit, delete;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            nrp = itemView.findViewById(R.id.nrp);
            nama = itemView.findViewById(R.id.nama);
            email = itemView.findViewById(R.id.email);
            jurusan = itemView.findViewById(R.id.jurusan);
            edit = itemView.findViewById(R.id.edit);
            delete = itemView.findViewById(R.id.delete);
        }
    }
}
