package com.gookkis.latihan3firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class NilaiMahasiswaActivity extends AppCompatActivity {

    Spinner spnMataKuliah;
    EditText etNilai;
    TextView tvNama;
    Button btnSimpan;

    ListView listViewNilai;
    List<NilaiModel> nilaiModelList = new ArrayList<>();

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference databaseNilai = database.getReference("nilai").child(Bantuan.idMahasiswa);


    @Override
    protected void onStart() {
        super.onStart();
        databaseNilai.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                nilaiModelList.clear();

                for (DataSnapshot dataNilai : dataSnapshot.getChildren()) {
                    nilaiModelList.add(dataNilai.getValue(NilaiModel.class));
                }

                NilaiMahasiswaAdapter nilaiMahasiswaAdapter = new NilaiMahasiswaAdapter(nilaiModelList, NilaiMahasiswaActivity.this);
                listViewNilai.setAdapter(nilaiMahasiswaAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nilai_mahasiswa);

        //final MahasiswaModel mahasiswaModel = (MahasiswaModel) getIntent().getSerializableExtra("mahasiswa");

        spnMataKuliah = findViewById(R.id.spn_mata_kuliah);
        tvNama = findViewById(R.id.tv_nama);
        etNilai = findViewById(R.id.et_nilai);
        btnSimpan = findViewById(R.id.btn_simpan);
        listViewNilai = findViewById(R.id.lv_nilai);

        tvNama.setText(Bantuan.namaMahasiswa);

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etNilai.getText().toString().isEmpty()) {
                    String id = databaseNilai.push().getKey();

                    NilaiModel nilaiModel = new NilaiModel(id, spnMataKuliah.getSelectedItem().toString(),
                            etNilai.getText().toString());

                    databaseNilai.child(id).setValue(nilaiModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(NilaiMahasiswaActivity.this, "Berhasil tersimpan!", Toast.LENGTH_LONG).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(NilaiMahasiswaActivity.this, "Gagal Tersimpan! " + e.getLocalizedMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    Toast.makeText(NilaiMahasiswaActivity.this, "Form tidak boleh kosong!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
