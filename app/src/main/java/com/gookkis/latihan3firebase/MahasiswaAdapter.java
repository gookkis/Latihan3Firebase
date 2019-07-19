package com.gookkis.latihan3firebase;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MahasiswaAdapter extends ArrayAdapter<MahasiswaModel> {
    private List<MahasiswaModel> mahasiswaModels;
    Activity mContext;

    public MahasiswaAdapter(List<MahasiswaModel> data, Activity context) {
        super(context, R.layout.item_mahasiswa, data);
        this.mahasiswaModels = data;
        this.mContext = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        MahasiswaModel mahasiswaModel = mahasiswaModels.get(position);

        View view = mContext.getLayoutInflater().inflate(R.layout.item_mahasiswa, null, true);

        TextView tvNama = view.findViewById(R.id.tv_nama);
        TextView tvProdi = view.findViewById(R.id.tv_podi);
        CircleImageView circleImageView = view.findViewById(R.id.profile_image);

        tvNama.setText(mahasiswaModel.getNama());
        tvProdi.setText(mahasiswaModel.getProdi());

        Glide.with(mContext).load(mahasiswaModel.getUrlGambar()).into(circleImageView);

        return view;

    }
}
