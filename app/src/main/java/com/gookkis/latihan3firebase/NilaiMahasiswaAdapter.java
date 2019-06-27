package com.gookkis.latihan3firebase;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class NilaiMahasiswaAdapter extends ArrayAdapter<NilaiModel> {
    private List<NilaiModel> nilaiModels;
    Activity mContext;

    public NilaiMahasiswaAdapter(List<NilaiModel> data, Activity context) {
        super(context, R.layout.item_mahasiswa, data);
        this.nilaiModels = data;
        this.mContext = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        NilaiModel nilaiModel = nilaiModels.get(position);

        View view = mContext.getLayoutInflater().inflate(R.layout.item_mahasiswa, null, true);

        TextView tvNama = view.findViewById(R.id.tv_nama);
        TextView tvProdi = view.findViewById(R.id.tv_podi);

        tvNama.setText(nilaiModel.getMatkul());
        tvProdi.setText(nilaiModel.getNilai());

        return view;

    }
}
